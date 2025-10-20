package org.uk.dog.portal.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestBean <T> implements Serializable {
    private T data;
    private String message;
}
