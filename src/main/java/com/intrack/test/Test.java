package com.intrack.test;

import com.intrack.session.Configuration;
import com.intrack.session.SqlSession;
import com.intrack.session.SqlSessionFactoryBuilder;
import com.intrack.session.defaults.DefaultSqlSession;
import com.intrack.session.defaults.DefaultSqlSessionFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * @author intrack
 */
public class Test {

    public static void main(String[] args) throws FileNotFoundException {
        //InputStream inputStream = new FileInputStream("F:\\idea_java_codes\\mybatis-wheel\\src\\main\\resources\\mybatis.xml");

        DefaultSqlSessionFactory sqlSessionFactory =
                (DefaultSqlSessionFactory) new SqlSessionFactoryBuilder().build("F:\\idea_java_codes\\mybatis-wheel\\src\\main\\resources\\mybatis.xml");

        SqlSession sqlSession = sqlSessionFactory.openSession();
//        SqlSession sqlSession = new DefaultSqlSession(new Configuration());

        User user0 = new User(2, "bei");

        User user = sqlSession.selectOne("com.intrack.test.UserDao.getUserById", user0);
        System.out.println(user);

        user = sqlSession.selectOne("com.intrack.test.UserDao.getUser");
        System.out.println(user);

        //user = sqlSession.selectOne("com.intrack.test.UserDao.getUserById", 2);
        //System.out.println(user);

        //System.out.println(sqlSession.insert("com.intrack.test.UserDao.insertOneById", 5));

        //System.out.print(sqlSession.update("com.intrack.test.User.updateOne", "beibei"));

        //System.out.println(sqlSession.insert("com.intrack.test.User.deleteOneById", 5));
    }

}
