package com.xuecheng.test.freemarker;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.test.freemarker.model.Student;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.*;
import java.util.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class FreemarkerTest {

    @Autowired
    GridFsTemplate gridFsTemplate;
    //基于模板生成静态化文件
    @Test
    public void testGenerateHtml() throws IOException, TemplateException {
        Configuration configuration=new Configuration(Configuration.getVersion());
        File file=new File(this.getClass().getResource("/").getPath()+"/templates/");
        configuration.setDirectoryForTemplateLoading(file);
        Template template = configuration.getTemplate("test1.ftl");
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, getMap());
        InputStream inputStream = IOUtils.toInputStream(content);
        FileOutputStream outputStream=new FileOutputStream(new File("D:/test.html"));
        IOUtils.copy(inputStream,outputStream);
        inputStream.close();
        outputStream.close();
    }

    @Test
    public void testStore2() throws FileNotFoundException {
        File file = new File("d:/course.ftl");
        FileInputStream inputStream = new FileInputStream(file);
//保存模版文件内容
        ObjectId objectId = gridFsTemplate.store(inputStream, "course.ftl");
        System.out.println(objectId);
        //5e207713e4d4fb2ee4a8c1a8
    }

    //基于模板字符串生成静态化文件
    @Test
    public void testGenerateHtmlByString() throws IOException, TemplateException {
        Configuration configuration=new Configuration(Configuration.getVersion());

        String templateString="" +
                "<html>\n" +
                "    <head></head>\n" +
                "    <body>\n" +
                "    名称：${name}\n" +
                "    </body>\n" +
                "</html>";
        StringTemplateLoader stringTemplateLoader=new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template",templateString);
        configuration.setTemplateLoader(stringTemplateLoader);
        Template template = configuration.getTemplate("template", "utf-8");
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, getMap());
        InputStream inputStream = IOUtils.toInputStream(content);
        FileOutputStream outputStream=new FileOutputStream(new File("D:/test.html"));
        IOUtils.copy(inputStream,outputStream);
        inputStream.close();
        outputStream.close();
    }

    //数据模型
    private Map getMap(){
        Map<String, Object> map = new HashMap<>();
        //向数据模型放数据
        map.put("name","程序员");
        Student stu1 = new Student();
        stu1.setName("小明");
        stu1.setAge(18);
        stu1.setMondy(1000.86f);
        stu1.setBirthday(new Date());
        Student stu2 = new Student();
        stu2.setName("小红");
        stu2.setMondy(200.1f);
        stu2.setAge(19);
//        stu2.setBirthday(new Date());
        List<Student> friends = new ArrayList<>();
        friends.add(stu1);
        stu2.setFriends(friends);
        stu2.setBestFriend(stu1);
        List<Student> stus = new ArrayList<>();
        stus.add(stu1);
        stus.add(stu2);
        //向数据模型放数据
        map.put("stus",stus);
        //准备map数据
        HashMap<String,Student> stuMap = new HashMap<>();
        stuMap.put("stu1",stu1);
        stuMap.put("stu2",stu2);
        //向数据模型放数据
        map.put("stu1",stu1);
        //向数据模型放数据
        map.put("stuMap",stuMap);
        return map;
    }
}
