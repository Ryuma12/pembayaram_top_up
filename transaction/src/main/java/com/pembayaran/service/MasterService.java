package com.pembayaran.service;

import com.pembayaran.model.Banner;
import com.pembayaran.model.ServiceProvide;
import com.pembayaran.repository.BannerRepository;
import com.pembayaran.repository.ServiceProvideRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class MasterService {

    @Autowired
    BannerRepository bannerRepository;

    @Autowired
    ServiceProvideRepository serviceProvideRepository;

    public Map getMasterBanner(){
        List<Banner> lb = IteratorUtils.toList(bannerRepository.findAll().iterator());
        Map response = new HashMap<>();
        List<Map> result = new LinkedList<>();
        response.put("status", 0);
        response.put("message", "Sukses");
        if (CollectionUtils.isNotEmpty(lb)){
            lb.forEach(e -> result.add(e.getJsonForList()));
        }
        response.put("data", result);

        return response;
    }

    public Map getMasterService() {
        List<ServiceProvide> lsp = serviceProvideRepository.findByActiveOrderById(Boolean.TRUE);
        Map response = new HashMap<>();
        List<Map> result = new LinkedList<>();
        response.put("status", 0);
        response.put("message", "Sukses");
        if (CollectionUtils.isNotEmpty(lsp)){
            lsp.forEach(e -> result.add(e.getJsonForList()));
        }
        response.put("data", result);

        return response;
    }
}
