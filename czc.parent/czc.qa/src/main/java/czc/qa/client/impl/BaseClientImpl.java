package czc.qa.client.impl;

import czc.qa.client.BaseClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.FacesRequestAttributes;

@Component
public class BaseClientImpl implements BaseClient {
    @Override
    public Result findById(String labelId) {
        return new Result(false, StatusCode.ERROR,"熔断器触发了");
    }
}
