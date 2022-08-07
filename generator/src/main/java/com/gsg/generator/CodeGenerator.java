package com.gsg.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Description: 代码生成类
 */
public class CodeGenerator {

    // 【注意】需要改成自己的MySQL/MariaDB的用户名
    public static String username = "root";
    // 【注意】需要改成自己的MySQL/MariaDB的密码
    public static String password = "root";
    // 【请确认】父级别包名称
    public static String parentPackage = "com.gsg";
    // 【请确认】模块名称，用于组成包名，将和上面的parentPackage组成完整的包名
    public static String modelName = "lottery";
    // 【请确认】数据库连接参数，需要注意数据库名称是否需要修改
    public static String url = "jdbc:mysql://127.0.0.1:3306/shuaigang?useAffectedRows=true&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true";
    // 连接数据库的驱动类
    public static String driver = "com.mysql.cj.jdbc.Driver";
    // 代码生成的目标路径
    public static String generateTo = "/generator/src/main/java";
    // mapper.xml的生成路径
    public static String mapperXmlPath = "/generator/src/main/resources/mapper";
    // 控制器的公共基类，用于抽象控制器的公共方法，null值表示没有父类
    public static String baseControllerClassName; // = "com.shuaigang.portal.base.BaseController";
    // 业务层的公共基类，用于抽象公共方法
    public static String baseServiceClassName; // = "com.shuaigang.portal.base.BaseServiceImpl";
    // 作者名
    public static String author = "shuaigang";
    // Mapper接口的模板文件，不用写后缀 .ftl
    public static String mapperTemplate = "/mapper.java";

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * RUN THIS
     */
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + generateTo);
        gc.setAuthor(author);
        gc.setOpen(false);
        //设置时间类型为Date
        gc.setDateType(DateType.TIME_PACK);

        //设置mapper.xml的resultMap
        gc.setBaseResultMap(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setDriverName(driver);
        dsc.setUsername(username);
        dsc.setPassword(password);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setEntity("model");
        pc.setModuleName(modelName);
        pc.setParent(parentPackage);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + mapperXmlPath
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));
        mpg.setTemplate(new TemplateConfig().setMapper(mapperTemplate));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 字段驼峰命名
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 设置实体类的lombok
        strategy.setEntityLombokModel(true);
        // 设置controller的父类
        if (baseControllerClassName != null) {
            strategy.setSuperControllerClass(baseControllerClassName);
        }
        // 设置服务类的父类
        if (baseServiceClassName != null) {
            strategy.setSuperServiceImplClass(baseServiceClassName);
        }
        // 设置实体类属性对应表字段的注解
        strategy.setEntityTableFieldAnnotationEnable(true);
        // 设置表名
        String tableName = scanner("表名, all全部表");
        if (!"all".equalsIgnoreCase(tableName)) {
            strategy.setInclude(tableName);
        }
        strategy.setTablePrefix(pc.getModuleName() + "_");
        strategy.setRestControllerStyle(true);
        mpg.setStrategy(strategy);

        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
