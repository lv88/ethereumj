package com.akcome.ethereum.starter.rest;


import com.akcome.ethereum.starter.ethereum.EthereumBean;
import com.akcome.ethereum.starter.model.AccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class MyRestController {

    @Autowired
    EthereumBean ethereumBean;

    @RequestMapping(value = "/bestBlock", method = GET)
    @ResponseBody
    public String getBestBlock() throws IOException {
        return ethereumBean.getBestBlock();
    }

    @RequestMapping(value = "/addAccount/{account}", method = POST)
    @ResponseBody
    public String addAccount(@PathVariable("account") String account) {
        return ethereumBean.addAccount(account);
    }

    @RequestMapping(value = "/listAccount", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<AccountInfo> listAccount() {
        return ethereumBean.listAccount();
    }

    @RequestMapping(value = "/doTransaction/{from}/{to}/{number}/{password}", method = POST)
    @ResponseBody
    public String doTransaction(@PathVariable("from") String from,@PathVariable("to") String to,@PathVariable("number") String number,@PathVariable("password") String password) {
        return ethereumBean.doTransaction(from,to,number,password);
    }
}
