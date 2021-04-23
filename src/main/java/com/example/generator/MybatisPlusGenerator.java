package com.example.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author yuelimin
 * @description
 * @since JDK 8
 */
public class MybatisPlusGenerator {

    // 是否生成DTO, 默认关闭
    private static final Boolean IS_DTO = true;

    /**
     * 获取控制台输入信息
     *
     * @param tip
     * @return
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + ": ");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "!");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        // 当前项目名
        String projectName = "/mybatis_plus_generator";

        globalConfig.setOutputDir(projectPath + projectName + "/src/main/java");
        globalConfig.setAuthor("yuelimin");
        globalConfig.setOpen(false);
        globalConfig.setIdType(IdType.ID_WORKER);
        // 关闭swagger注解
        // globalConfig.setSwagger2(false);
        autoGenerator.setGlobalConfig(globalConfig);

        // 数据源配置 需配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();

        // 配置需要进行代码生成的数据库地址
        dataSourceConfig
                .setUrl("jdbc:mysql://192.168.158.160:3306/mybatis_plus?serverTimezone=Asia/Shanghai");

        // dataSourceConfig.setSchemaName("public");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        // 数据库用户名
        dataSourceConfig.setUsername("root");
        // 数据库密码
        dataSourceConfig.setPassword("yueliminvc@outlook.com");
        autoGenerator.setDataSource(dataSourceConfig);

        // 生成包配置
        PackageConfig packageConfig = new PackageConfig();
        // 包名
        packageConfig.setParent("com.example");
        // 如果需要手动输入模块名
        packageConfig.setModuleName(scanner("模块名"));
        autoGenerator.setPackageInfo(packageConfig);

        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();

        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {

                // 自定义输出文件名
                return projectPath
                        + projectName
                        + "/src/main/resources/mapper/"
                        + packageConfig.getModuleName()
                        + "/" + tableInfo.getEntityName()
                        + "Mapper" + StringPool.DOT_XML;
            }
        });

        injectionConfig.setFileOutConfigList(focList);
        autoGenerator.setCfg(injectionConfig);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        // 指定自定义模板路径, 注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity-test.java");
        // templateConfig.setService("templates/service.java");
        // templateConfig.setController("templates/controller.java");

        templateConfig.setXml(null);
        autoGenerator.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        // 表名映射到实体策略, 带下划线的转成驼峰
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        // 列名映射到类型属性策略, 带下划线的转成驼峰
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        // strategyConfig.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        // 实体类使用lombok
        strategyConfig.setEntityLombokModel(true);
        // 使用RestController风格
        strategyConfig.setRestControllerStyle(true);
        // strategyConfig.setSuperControllerClass("com.baomidou.ant.common.BaseController");

        // 如果 setInclude()
        // 设置表名不加参数, 会自动查找所有表
        // 如需要制定单个表, 需填写参数如: strategyConfig.setInclude("user_info);
        strategyConfig.setInclude();
        // strategyConfig.setSuperEntityColumns("id");
        // strategyConfig.setControllerMappingHyphenStyle(true);

        // 自动将数据库中表名为 user_info 格式的转为 UserInfo 命名
        // 表名映射到实体名称去掉前缀
        strategyConfig.setTablePrefix(packageConfig.getModuleName() + "_");
        // Boolean类型字段是否移除is前缀处理
        strategyConfig.setEntityBooleanColumnRemoveIsPrefix(true);
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());

        System.out.println("===================== MyBatis Plus Generator ==================");

        if (IS_DTO) {
            // swagger注解
            globalConfig.setSwagger2(true);
            globalConfig.setEntityName("%sDto");
            packageConfig.setEntity("dto");
        }

        autoGenerator.execute();

        System.out.println("================= MyBatis Plus Generator Execute Complete ==================");
    }
}
