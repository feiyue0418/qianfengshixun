package com.ziru.test;

import com.ziru.App;
import com.ziru.bean.Admins;
import com.ziru.mapper.AdminsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;


@RunWith(SpringRunner.class) //测试SpringBoot框架。启动SpringBoot框架
@SpringBootTest(classes = App.class)
public class TestAdmins {

    @Resource
    AdminsMapper adminsMapper;

    @Test
    public void testList(){
        List<Admins> list = adminsMapper.list();
        for (Admins admins : list) {
            System.out.println(admins);
        }
    }

    @Test
    public void testInser(){
        Admins admins=new Admins(null,"bruce","123456","0","TENANT");
        int count = adminsMapper.insert(admins);
        System.out.println(count>0?"新增成功":"新增失败");
    }
}
