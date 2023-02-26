package com.hf.es.service;

import com.hf.es.repo.EsBookRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author xiehongfei
 * @description
 * @date 2023/2/25 19:30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EsDataService {

    private final EsBookRepo esBookRepo;


}
