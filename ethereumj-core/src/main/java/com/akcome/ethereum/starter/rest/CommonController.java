package com.akcome.ethereum.starter.rest;



import com.akcome.ethereum.starter.ethereum.EthereumBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {

    @Autowired
    EthereumBean ethereumBean;

    @RequestMapping(value = {"/","/list"})
    public String index(ModelMap map) {
        // 加入一个属性，用来在模板中读取
        map.addAttribute("host", "http://blog.didispace.com");
        map.addAttribute("accountList",ethereumBean.listAccount());
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return "index";
    }

    @RequestMapping(value = "/addAccount")
    public String addAccount(ModelMap map) {
        return "addAccount";
    }

    @RequestMapping(value = "/transfer")
    public String transfer(ModelMap map) {
        return "transfer";
    }


}
