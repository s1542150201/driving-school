package io.driving.modules.business.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.driving.common.exception.RRException;
import io.driving.common.utils.FileUploadUtils;
import io.driving.common.utils.ServletUtils;
import io.driving.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.driving.modules.business.entity.MedicalEntity;
import io.driving.modules.business.service.MedicalService;
import io.driving.common.utils.PageUtils;
import io.driving.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * 体检信息
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2022-03-19 15:33:27
 */
@RestController
@RequestMapping("business/medical")
public class MedicalController {
    @Autowired
    private MedicalService medicalService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = medicalService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:medical:delete")
    public R delete(@RequestBody Long[] ids){
        medicalService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    @RequiresPermissions("business:medical:upload")
    public R upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }
        try {
            // 上传文件路径
            String filePath = "D:/driving/uploadPath";
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = this.getUrl() + fileName;
            MedicalEntity me = new MedicalEntity();
            me.setCreateTime(new Date());
            me.setUserId(ShiroUtils.getUserId());
            me.setFileName(ShiroUtils.getUserEntity().getRealName()+"--体检报告");
            me.setFilePath(url);
            medicalService.save(me);
            Map<String,Object> map = new HashMap<>();
            map.put("fileName", fileName);
            map.put("url", url);
            return R.ok().put("map", map);
        }
        catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    private String getUrl() {
        HttpServletRequest request = ServletUtils.getRequest();
        return getDomain(request);
    }

    public static String getDomain(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String contextPath = request.getServletContext().getContextPath();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
    }

    /**
     * 上传文件
     */
    @RequestMapping("/down")
    @RequiresPermissions("business:medical:down")
    public R down(@RequestParam("id") String id){
        //设置文件路径
        MedicalEntity me = medicalService.getById(id);
        String path = me.getFilePath();
        return R.ok().put("path", path);
    }

}
