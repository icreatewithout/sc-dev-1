package com.ifeb2.userservice.service.impl;

import com.ifeb2.scdevbase.service.BaseService;
import com.ifeb2.userservice.domain.ScDict;
import com.ifeb2.userservice.repository.ScDictRepository;
import com.ifeb2.userservice.service.ScDictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScDictServiceImpl extends BaseService<ScDict, ScDictRepository> implements ScDictService {

}
