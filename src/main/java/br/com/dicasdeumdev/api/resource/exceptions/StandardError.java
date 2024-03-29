package br.com.dicasdeumdev.api.resource.exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class StandardError {
    
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String path;
}
