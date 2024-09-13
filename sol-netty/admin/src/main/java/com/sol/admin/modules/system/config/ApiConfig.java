package com.sol.admin.modules.system.config;

import com.sol.admin.modules.system.entity.SysPermission;
import com.sol.admin.modules.system.service.SysPermissionService;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.customizers.RouterOperationCustomizer;
import org.springdoc.core.filters.OpenApiMethodFilter;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.providers.SpringDocProviders;
import org.springdoc.core.providers.SpringWebProvider;
import org.springdoc.core.service.AbstractRequestService;
import org.springdoc.core.service.GenericResponseService;
import org.springdoc.core.service.OpenAPIService;
import org.springdoc.core.service.OperationService;
import org.springdoc.webmvc.api.OpenApiResource;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Component
public class ApiConfig extends OpenApiResource {


    public ApiConfig(String groupName,
                     ObjectFactory<OpenAPIService> openAPIBuilderObjectFactory,
                     AbstractRequestService requestBuilder,
                     GenericResponseService responseBuilder,
                     OperationService operationParser,
                     Optional<List<OperationCustomizer>> operationCustomizers,
                     Optional<List<OpenApiCustomizer>> openApiCustomizers,
                     Optional<List<RouterOperationCustomizer>> routerOperationCustomizers,
                     Optional<List<OpenApiMethodFilter>> methodFilters,
                     SpringDocConfigProperties springDocConfigProperties,
                     SpringDocProviders springDocProviders) {
        super(groupName, openAPIBuilderObjectFactory, requestBuilder, responseBuilder,
                operationParser, operationCustomizers, openApiCustomizers,
                routerOperationCustomizers, methodFilters, springDocConfigProperties, springDocProviders);
    }
    @Autowired
    public ApiConfig(ObjectFactory<OpenAPIService> openAPIBuilderObjectFactory,
                     AbstractRequestService requestBuilder,
                     GenericResponseService responseBuilder,
                     OperationService operationParser,
                     Optional<List<OperationCustomizer>> operationCustomizers,
                     Optional<List<OpenApiCustomizer>> openApiCustomizers,
                     Optional<List<RouterOperationCustomizer>> routerOperationCustomizers,
                     Optional<List<OpenApiMethodFilter>> methodFilters,
                     SpringDocConfigProperties springDocConfigProperties,
                     SpringDocProviders springDocProviders) {
        super(openAPIBuilderObjectFactory, requestBuilder, responseBuilder,
                operationParser, operationCustomizers, openApiCustomizers,
                routerOperationCustomizers, methodFilters, springDocConfigProperties, springDocProviders);
    }

    @Override
    protected String getServerUrl(HttpServletRequest request, String apiDocsUrl) {
        String requestUrl = decode(request.getRequestURL().toString());
        Optional<SpringWebProvider> springWebProviderOptional = springDocProviders.getSpringWebProvider();
        String prefix = StringUtils.EMPTY;
        if (springWebProviderOptional.isPresent())
            prefix = springWebProviderOptional.get().findPathPrefix(springDocConfigProperties);
        return requestUrl.substring(0, requestUrl.length() - apiDocsUrl.length() - prefix.length());
    }

    @Autowired
    SysPermissionService service;

    /**
     * 在这里设置默认的api信息，放到数据库里
     */
    @PostConstruct
    public void initApi() {

        log.info("===================== api 初始化设置 =====================");
        // 获取 swagger 生成的 api 信息
        OpenAPI openApi = super.getOpenApi(null);

        // 设置控制器的 root
        setRoot(openApi);
        // 设置具体的api路劲与描述
        setApis(openApi);

    }
    public void setRoot(OpenAPI openApi){
        List<Tag> tags = openApi.getTags();
        List<Tag> sysPermissions = service.getTags(); // 从数据库中获取所有根节点的标签

        // 求差集
        List<Tag> tagsToInsert = new ArrayList<>(tags);
        tagsToInsert.removeAll(sysPermissions);
        List<Tag> tagsToRemove = new ArrayList<>(sysPermissions);
        tagsToRemove.removeAll(tags);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        // 将需要插入的标签转换为 SysPermission 对象
        List<SysPermission> newPermissions = tagsToInsert.stream()
                .map(tag -> SysPermission.builder()
                        .name(tag.getName())
                        .type(0)
                        .description(tag.getDescription())
                        .createdTime(timestamp)
                        .updatedTime(timestamp)
                        .build())
                .collect(Collectors.toList());
        // 批量插入新权限到数据库
        if (!newPermissions.isEmpty()) {
            service.insertBatch(newPermissions);
        }
        // 将需要删除的 api
        List<String> delPermissions = tagsToRemove.stream().map(Tag::getName).toList();
        // 批量插入新权限到数据库
        if (!delPermissions.isEmpty()) {
            service.delAll(delPermissions);
        }
    }

    public void setApis(OpenAPI openApi){
        // swagger 接口路径列表
        Map<String, PathItem> map = openApi.getPaths(); // PathItem
        // 数据库中保存的接口路径列表
        List<SysPermission> dbPaths = service.getIsNotRoot();

        List<String> paths = dbPaths.stream().map(SysPermission::getName).toList();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<SysPermission> newPaths = new ArrayList<>();
        // 增加 新接口
        for (String path : map.keySet()) {
            if (!paths.contains(path)) {
                PathItem pathItem = map.get(path);
                // 定义 HTTP 方法的所有可能
                List<Operation> operations = Arrays.asList(
                        pathItem.getGet(),
                        pathItem.getPost(),
                        pathItem.getPut(),
                        pathItem.getDelete(),
                        pathItem.getOptions(),
                        pathItem.getHead(),
                        pathItem.getPatch(),
                        pathItem.getTrace()
                );
                // 获取第一个非空的 Operation 对象的 summary
                String summary = operations.stream()
                        .filter(Objects::nonNull)
                        .map(Operation::getSummary)
                        .filter(Objects::nonNull) // 确保 summary 不为 null
                        .findFirst()
                        .orElse("No summary");
                SysPermission sysPermission = SysPermission.builder()
                        .name(path)
                        .type(1)
                        .description(summary)
                        .createdTime(timestamp)
                        .updatedTime(timestamp)
                        .build();
                newPaths.add(sysPermission);
            }
        }
        // 批量保存新的接口路径
        if (!newPaths.isEmpty()) {
            service.insertBatch(newPaths);
        }

        List<Long> ids = dbPaths.stream().filter(sysPermission -> !paths.contains(sysPermission.getName())).map(SysPermission::getId).toList();
        // 删除 系统中不存在的接口 (接口路径改变，或者删除)
        if (!ids.isEmpty()) {
            service.removeByIds(ids);
        }
    }
}