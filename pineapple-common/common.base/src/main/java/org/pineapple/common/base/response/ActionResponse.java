package org.pineapple.common.base.response;

import lombok.Getter;
import lombok.Setter;
import org.pineapple.common.base.Model;

/**
 * <p>动作响应</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/15
 */
@Setter
@Getter
public class ActionResponse<D> implements Model {
    private static final long serialVersionUID = 1L;

    private int code;

    private String message;

    private D data;
}
