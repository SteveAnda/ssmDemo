package com.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GeneratorSqlmap {

    public void generator() throws Exception{

        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;

        //指定 逆向工程配置文件
        /*这种绝对路径的方式是可以的*/
//        File configFile = new File("/work/IdeaProjects/ssmDemo/mybatis-generator/src/main/java/com/generator/generatorConfig.xml");

        /*这种方式还是不行,因为项目的工程目录是ssmDemo,所以classpath是ssmemo */
//        File configFile = new File("generatorConfig.xml");


        /*这种方式也不行, 目标资源的实际路径:/work/IdeaProjects/ssmDemo/mybatis-generator/target/classes/generatorConfig.xml
         * 所以是因为没有把项目打成war包吗?*/
//        File configFile = new File("mybatis-generator/generatorConfig.xml");

        /*这种方式是可以的,工程目录下的mybatis-generator模块(目录)的文件夹*/
//        File configFile = new File("mybatis-generator/src/main/java/com/generator/generatorConfig.xml");

        /* 这种方式不行,因为target子文件夹中不会生成.xml文件
        (.xml文件只会读取resources目录下的,java目录不会读取并且生成到target目录),这只是普通java程序,没有打war包,
        如果需要用target下的.class文件来运行,则pom.xml中需要配置<packaging>war</packaging>
        设置<packaging>war</packaging>也不行.....
        */
//        File configFile = new File("generatorConfig.xml");

        /*以上所有测试,run configuration均为:
        * working directory:  /work/IdeaProjects/ssmDemo
        * use classpath of module:  mybatis-generator
        * */

        /*根本原因还是在run configurations配置上
        *
        * 更改配置文件
        * working directory:  $MODULE_DIR$
        * use classpath of module:  mybatis-generator
        * */

        File configFile = new File("src/main/java/com/generator/generatorConfig.xml");

        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);

    }
    public static void main(String[] args) throws Exception {
        try {
            GeneratorSqlmap generatorSqlmap = new GeneratorSqlmap();
            generatorSqlmap.generator();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
