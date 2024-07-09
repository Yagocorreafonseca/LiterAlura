package com.alura.LiterAlura.Service;

public interface InterfaceConvert {
    <T> T obterDados(String json, Class<T> classe);
}