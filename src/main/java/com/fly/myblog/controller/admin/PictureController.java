package com.fly.myblog.controller.admin;

import com.fly.myblog.entity.Picture;
import com.fly.myblog.service.PictureService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    //显示全部图片
    @GetMapping("/pictures")
    public String pictures(Model model, @RequestParam(defaultValue = "1" ,value = "pageNum" ) Integer pageNum){
        PageHelper.startPage(pageNum,5);
        List<Picture> pictures = pictureService.listPicture();
        PageInfo<Picture> pageInfo = new PageInfo<>(pictures);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/pictures";
    }

    //跳转到添加页面
    @GetMapping("/pictures/input")
    public String input(Model model){
        model.addAttribute("picture",new Picture());
        return "admin/pictures-input";
    }

    //添加页面
    @PostMapping("/pictures")
    public String post(@Valid Picture picture, BindingResult result, RedirectAttributes attributes){

        if (result.hasErrors()){
            return "admin/pictures-input";

        }
        int i = pictureService.savePicture(picture);
        if (i == 0){
            attributes.addFlashAttribute("message","添加失败");
        }else {
            attributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/pictures";
    }


    //跳转到编辑页面
    @GetMapping("/pictures/{id}/input")
    public String editInput(@PathVariable Long id , Model model){
        Picture picture = pictureService.getPictureById(id);
        model.addAttribute("picture",picture);
        return "admin/pictures-input";
    }

    //编辑页面
    @PostMapping("/pictures/{id}")
    public String edit(@Valid Picture picture,RedirectAttributes attributes ){
        int i = pictureService.updatePicture(picture);
        if (i == 0){
            attributes.addFlashAttribute("message","编辑失败");
        }else {
            attributes.addFlashAttribute("message","编辑成功");
        }
        return "redirect:/admin/pictures";
    }

    //删除
    @GetMapping("/pictures/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){

        pictureService.deletePicture(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/pictures";
    }


}
