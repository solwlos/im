package com.sol.admin.nettyWebsocket.controller;


import com.groupdocs.viewer.Viewer;
import com.groupdocs.viewer.options.HtmlViewOptions;
import com.sol.admin.nettyWebsocket.NioWebSocketChannelPool;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/server")
@Tag(name = "/server", description = "服务端管理")
public class ServerController {


    @Autowired
    private NioWebSocketChannelPool nioWebSocketChannelPool;

    @GetMapping("/count")
    @Operation(summary ="查看在线人数")
    public Integer count(){
        return nioWebSocketChannelPool.count();
    }

    @GetMapping("/view")
    @Operation(summary = "查看在线人数")
    public String view() {
        // 设置输入 PLT 文件
        String filePath = "D:\\python\\study\\input.plt";

        try (Viewer viewer = new Viewer(filePath)) {
            // 设置视图选项
            HtmlViewOptions viewOptions = HtmlViewOptions.forEmbeddedResources();

            // 定义要生成的HTML文件的路径对象，这里假设文件名为"page.html"
            Path htmlFilePath = Paths.get("D:\\python\\study\\page.html");
            // 创建文件所在目录（如果不存在），并创建文件（如果不存在）
            Files.createDirectories(htmlFilePath.getParent());
            Files.createFile(htmlFilePath);

            // 将 PLT 文件渲染为带有嵌入资源的 HTML，渲染结果会写入到page.html文件中（根据GroupDocs.Viewer配置）
            viewer.view(viewOptions);

            // 读取生成的HTML文件内容
            return Files.readString(htmlFilePath);
        } catch (IOException e) {
            log.error("读取渲染后的HTML文件出现错误", e);
            return "读取渲染后的HTML文件出现错误";
        }
    }
}
