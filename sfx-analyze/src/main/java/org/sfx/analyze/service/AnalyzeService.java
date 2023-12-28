package org.sfx.analyze.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.val;
import org.sfx.analyze.dao.AnalyzeMapper;
import org.sfx.analyze.domain.IncAnalyze;
import org.sfx.api.domain.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AnalyzeService {
    @Autowired
    AnalyzeMapper mapper;

    private IncAnalyze GetByDm(String dm) throws NoResultException {
        LambdaQueryWrapper<IncAnalyze> lw = new LambdaQueryWrapper<IncAnalyze>();
        lw.eq(IncAnalyze::getDm, dm);
        val incAnalyzes = mapper.selectList(lw);
        if (incAnalyzes.size() == 0) {
            throw new NoResultException();
        }
        return incAnalyzes.get(0);
    }

    /**
     * 盈利能力
     *
     * @param dm
     * @return
     */
    public ResponseResult<ArrayList<String>> GetData1(String dm) {
        ResponseResult<ArrayList<String>> result = new ResponseResult<ArrayList<String>>();
        try {
            result.setData(new ArrayList<String>());
            result.setCode(200);
            IncAnalyze a = GetByDm(dm);
            result.getData().add(a.getJzcsy());
            result.getData().add(a.getMll());
            result.getData().add(a.getJll());
            return result;
        } catch (NoResultException e) {
            result.setCode(4302);
            result.setMsg("股票信息错误");
            return result;
        }
    }

    /**
     * 运营能力
     *
     * @param dm
     * @return
     */
    public ResponseResult<ArrayList<String>> GetData2(String dm) {
        ResponseResult<ArrayList<String>> result = new ResponseResult<ArrayList<String>>();
        try {
            result.setData(new ArrayList<String>());
            result.setCode(200);
            IncAnalyze a = GetByDm(dm);
            result.getData().add(a.getYszzzl());
            result.getData().add(a.getChzzl());
            result.getData().add(a.getLdzczzl());
            return result;
        } catch (NoResultException e) {
            result.setCode(4302);
            result.setMsg("股票信息错误");
            return result;
        }
    }

    /**
     * 成长能力
     *
     * @param dm
     * @return
     */
    public ResponseResult<ArrayList<String>> GetData3(String dm) {
        ResponseResult<ArrayList<String>> result = new ResponseResult<ArrayList<String>>();
        try {
            result.setData(new ArrayList<String>());
            result.setCode(200);
            IncAnalyze a = GetByDm(dm);
            result.getData().add(a.getZyzzl());
            result.getData().add(a.getJlrzzl());
            result.getData().add(a.getJzczzl());
            result.getData().add(a.getZzczzl());
            result.getData().add(a.getMgzzl());
            result.getData().add(a.getGdzzl());
            return result;
        } catch (NoResultException e) {
            result.setCode(4302);
            result.setMsg("股票信息错误");
            return result;
        }
    }

    /**
     * 偿债能力
     *
     * @param dm
     * @return
     */
    public ResponseResult<ArrayList<String>> GetData4(String dm) {
        ResponseResult<ArrayList<String>> result = new ResponseResult<ArrayList<String>>();
        try {
            result.setData(new ArrayList<String>());
            result.setCode(200);
            IncAnalyze a = GetByDm(dm);
            result.getData().add(a.getLdbl());
            result.getData().add(a.getSdbl());
            result.getData().add(a.getXjbl());
            result.getData().add(a.getGdbl());
            result.getData().add(a.getZcfzl());
            return result;
        } catch (NoResultException e) {
            result.setCode(4302);
            result.setMsg("股票信息错误");
            return result;
        }
    }

    private class NoResultException extends Exception {
    }
}
