package ru.alex.taskmanagementsystem.mapper;

public interface Mapper <E,D>{

    default E toEntity(D dto){
        return null;
    }

    default D toDto(E entity){
        return null;
    }

}
