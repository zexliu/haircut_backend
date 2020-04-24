package com.zex.cloud.haircut.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.zex.cloud.haircut.config.JsonLongSerializer;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

public abstract class AbstractTree<T> {

     @JsonSerialize(using = JsonLongSerializer.class)
     private Long id ;

     @JsonSerialize(using = JsonLongSerializer.class)
     private Long parentId;

     private List<T> children;

     private Integer level;

     public Long getId() {
          return id;
     }

     public void setId(Long id) {
          this.id = id;
     }

     public Long getParentId() {
          return parentId;
     }

     public void setParentId(Long parentId) {
          this.parentId = parentId;
     }

     public List<T> getChildren() {
          return children;
     }

     public void setChildren(List<T> children) {
          this.children = children;
     }


     public Integer getLevel() {
          return level;
     }

     public void setLevel(Integer level) {
          this.level = level;
     }

     public static <T extends AbstractTree<T>> List<T> listToTree(List<T> list){
          if (CollectionUtils.isEmpty(list)){
               return Lists.newArrayList();
          }else {
               Multimap<Long, T> multimap = ArrayListMultimap.create();
               List<T> rootList = Lists.newArrayList();
               for (T module : list) {
                    if (module.getParentId() == 0){ //根目录
                         rootList.add(module);
                    }else {
                         multimap.put(module.getParentId(),module);
                    }
               }
               transformTree(rootList,multimap);
               return rootList;
          }
     }

     public static <T extends AbstractTree<T>> List<T> listToTree(List<T> list,Integer level){
          if (CollectionUtils.isEmpty(list)){
               return Lists.newArrayList();
          }else {
               Multimap<Long, T> multimap = ArrayListMultimap.create();
               List<T> rootList = Lists.newArrayList();
               for (T module : list) {
                    if (module.getLevel().equals(level)){ //根目录
                         rootList.add(module);
                    }else {
                         multimap.put(module.getParentId(),module);
                    }
               }
               transformTree(rootList,multimap);
               return rootList;
          }
     }


     private static <T extends AbstractTree<T>> void transformTree(List<T> rootList, Multimap<Long, T> multimap) {
          //便利当前层级
          for (T t : rootList) {
               //获取子列表
               List<T> children = (List<T>) multimap.get(t.getId());
               if (CollectionUtils.isNotEmpty(children)){
                    //设置子列表
                    t.setChildren(children);
                    //进入下一层级处理
                    transformTree(children,multimap);
               }
          }
     }
}
