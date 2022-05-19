package com.ifeb2.scdevcore.component;

import com.ifeb2.scdevbase.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component(value = "auditorAwareComponent")
public class AuditorAwareComponent implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        try {
            Long id = SecurityUtils.getLoginUser().getUserId();
            return Optional.ofNullable(id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
