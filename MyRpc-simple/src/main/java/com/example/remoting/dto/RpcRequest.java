package com.example.remoting.dto;

import lombok.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Description 请求
 *
 * @Author heyong
 * @Date 2022/5/9 19:35
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;
    private String requestId;
    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
    private String version;
    private String group;

    public String getRpcServiceName() {
        return this.getInterfaceName() + this.getGroup() + this.getVersion();
    }
}
