package com.fly.myblog.controller.admin;

import com.fly.myblog.entity.Type;
import com.fly.myblog.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeDaoService;

    //分页查询分类列表
    @GetMapping("/types")
    public String list(Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        //安装排序字段 倒序 排序
        String orderBy = "id desc";
        PageHelper.startPage(pageNum,10,orderBy);
        List<Type> typeList = typeDaoService.getAllType();
        PageInfo<Type> pageInfo = new PageInfo<>(typeList);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/types";
    }

    //返回到新增分类页面
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    //新增分类
    @PostMapping("/types")
    public String post(@Valid Type type, RedirectAttributes attributes){
        //根据分类名称查询，添加分类不能重复
      Type type1 =  typeDaoService.getTypeByName(type.getName());
      if (type1!= null){
            attributes.addFlashAttribute("message","不能添加重复的分类");
            return "redirect:/admin/types/input";
      }
      int t = typeDaoService.saveType(type);
      if (t == 0){
          attributes.addFlashAttribute("message","添加成功");
      }else {
          attributes.addFlashAttribute("message","添加失败");
      }

      return "redirect:/admin/types";

    }
    //转跳到修改分类页面  根据ID获取类型
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        Type type = typeDaoService.getTypeById(id);
        model.addAttribute("type",type);
        return "admin/types-input";
    }

    //编辑修改分类
    @PostMapping("types/{id}")
    public String editPost(@Valid Type type, RedirectAttributes attributes){
        Type type1 = typeDaoService.getTypeByName(type.getName());
        if (type1 != null) {
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "redirect:/admin/types/{id}/input";
        }
        int t = typeDaoService.updateType(type);
        if (t == 0){
            attributes.addFlashAttribute("message","编辑失败");
        }else {
            attributes.addFlashAttribute("message","编辑成功");
        }

        return "redirect:/admin/types";
    }

    //删除分类
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeDaoService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }




}
