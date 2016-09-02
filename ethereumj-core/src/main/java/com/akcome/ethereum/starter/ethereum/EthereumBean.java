package com.akcome.ethereum.starter.ethereum;

import com.akcome.ethereum.starter.model.AccountInfo;
import org.apache.commons.lang3.StringUtils;
import org.ethereum.core.AccountState;
import org.ethereum.core.Block;
import org.ethereum.core.Repository;
import org.ethereum.core.Transaction;
import org.ethereum.crypto.ECKey;
import org.ethereum.facade.Ethereum;
import org.ethereum.jsonrpc.JsonRpc;
import org.ethereum.jsonrpc.TypeConverter;
import org.ethereum.mine.MinerListener;
import org.ethereum.util.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.ethereum.crypto.HashUtil.sha3;


public class EthereumBean {

    private final static Logger logger = LoggerFactory.getLogger(EthereumBean.class);

    @Autowired
    Ethereum ethereum;

    @Autowired
    JsonRpc jsonRpc;
    @Autowired
    Repository repository;

    public void start(){
//        this.ethereum = EthereumFactory.createEthereum();
        while(ethereum==null){
            //解决Ethereum构建比EthereumBean晚的问题，避免NP
        }
        ethereum.addListener(new EthereumListener(ethereum));

        ethereum.getBlockMiner().addListener(new MinerListener() {
            @Override
            public void miningStarted() {
                logger.info("Miner started");
            }

            @Override
            public void miningStopped() {
                logger.info("Miner stopped");
            }

            @Override
            public void blockMiningStarted(Block block) {
                logger.info("Start mining block: " + block.getShortDescr());
            }

            @Override
            public void blockMined(Block block) {
                logger.info("Block mined! : \n" + block);
            }

            @Override
            public void blockMiningCanceled(Block block) {
                logger.info("Cancel mining block: " + block.getShortDescr());
            }
        });
        ethereum.getBlockMiner().startMining();
    }


    public String getBestBlock(){
        return "" + ethereum.getBlockchain().getBestBlock().getNumber();
    }

    public String addAccount(String account){
        try {
            String cowAcct = jsonRpc.personal_newAccount(account);
            byte[] newAddress = TypeConverter.StringHexToByteArray(cowAcct);
            if (!repository.isExist(newAddress)) {
                AccountState accountState = repository.createAccount(newAddress);
                return String.format("创建账户成功，新账户地址是：%s",TypeConverter.toJsonHex(newAddress));
            }
            return "账户已存在";
        } catch (Exception e){
            return "创建账户失败";
        }
    }

    public List<AccountInfo> listAccount(){
        List<AccountInfo> accountInfoList=new ArrayList<>();
        for (byte[] account : repository.getAccountsKeys()) {
            accountInfoList.add(new AccountInfo(TypeConverter.toJsonHex(account),repository.getAccountState(account).getBalance()));
//            accountMsg.append(String.format("%s ==%s", TypeConverter.toJsonHex(account), repository.getAccountState(account).getBalance().toString()));
//            accountMsg.append("\n");
        }
        return accountInfoList;
    }

    public String doTransaction(String from, String to,String number,String seed){
        try {
            ECKey senderKey = ECKey.fromPrivate(sha3(seed.getBytes()));
            if (!from.startsWith("0x")) {
                from="0x"+from;
            }
            if (!StringUtils.equals(from, TypeConverter.toJsonHex(senderKey.getAddress()))) {
                return "转账账户密码不正确";
            }
//        ECKey senderKey = ECKey.fromPrivate(Hex.decode("6ef8da380c27cea8fdf7448340ea99e8e2268fc2950d79ed47cbf6f85dc977ec"));
            byte[] receiverAddr = TypeConverter.StringHexToByteArray(to);//"5db10750e8caff27f906b41c71b3471057dd2004"
            StringBuilder msg = new StringBuilder();
            msg.append("before transaction").append("\n");
            msg.append(listAccount());
            int nonce = ethereum.getRepository().getNonce(senderKey.getAddress()).intValue();

            Transaction tx = new Transaction(ByteUtil.intToBytesNoLeadZeroes(nonce),
                    ByteUtil.longToBytesNoLeadZeroes(50_000_000_000L), ByteUtil.longToBytesNoLeadZeroes(0xfffff),
                    receiverAddr, ByteUtil.longToBytesNoLeadZeroes(Long.parseLong(number)), new byte[0]);
            tx.sign(senderKey);
            logger.info("<== Submitting tx: " + tx);
            ethereum.submitTransaction(tx);
//        msg.append("after transaction").append("\n");
//        msg.append(listAccount());
            logger.info(msg.toString());
            return "转账成功";
        } catch (Exception e){
            logger.error("doTransaction fails",e);
            return "转账失败";
        }
    }
}
