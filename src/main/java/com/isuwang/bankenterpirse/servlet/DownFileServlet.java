package com.isuwang.bankenterpirse.servlet;

import com.alibaba.fastjson.JSON;
import com.isuwang.bankenterpirse.vo.Res;
import com.isuwang.bankenterpirse.properties.ConfigContext;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@WebServlet(value = "/down", initParams = {
        @WebInitParam(name = "log4j", value = "log4j.properties")
})
public class DownFileServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(DownFileServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dirPath = ConfigContext.getDirPath();
        if (dirPath == null || "".equals(dirPath)) {
            logger.error("服务器内部配置异常");
            responseFail(resp, Res.error("服务器内部配置异常"));
            return;
        }
        String fileName = new String(req.getParameter("fileName").getBytes("iso-8859-1"), "UTF-8");
        logger.info("====开始下载文件[" + fileName + "]====");
        File file = new File(dirPath, fileName);
        if (!file.exists()) {
            logger.error("====文件[" + fileName + "]不存在====");
            responseFail(resp, Res.error("文件不存在"));
            return;
        }
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        ServletOutputStream os = resp.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        byte[] bytes = new byte[1024 * 2];
        int len = 0;
        while ((len = bis.read(bytes)) != -1) {
            os.write(bytes, 0, len);
        }
        logger.info("====下载文件[" + fileName + "]成功====");
        os.close();
        bis.close();
    }

    public void responseFail(HttpServletResponse resp, Res res) throws IOException {
        resp.setCharacterEncoding("utf-8");
        resp.setStatus(500);
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.println(JSON.toJSONString(res));
        writer.close();
    }
}
