package com.cursoalura.literalura_challenge.servicios;

public interface IConversor {
    <T> T convertir(String json, Class<T> clase);
}
