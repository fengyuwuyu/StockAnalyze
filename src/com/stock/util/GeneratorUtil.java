package com.stock.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class GeneratorUtil {
	
	public static void main(String[] args) throws Exception{
		generator();
	}
	
	public static void generator() throws Exception {
        //信息缓存
        List<String> warnings = new ArrayList<String>();
        //覆盖已有的重名文件
        boolean overwrite = true;
        //准备 配置文件
        File configFile = new File("C://Users//ll//git//StockAnalyze//WebRoot//WEB-INF//classes/generatorConfig.xml");
        //创建 配置解析器
        ConfigurationParser parser = new ConfigurationParser(warnings);
        //获取 配置信息
        Configuration config = parser.parseConfiguration(configFile);
        //创建 默认命令解释调回器
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        //创建生成器
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        //执行并关闭生成器
        myBatisGenerator.generate(null);
	}
}
