package com.thinkgem.jeesite.modules.user.entity;


import java.util.List;

/**
 * author luo
 * 网络图数据实体类
 */
public class TreeData {
    private String text;
    private List<TreeData> nodes ;

    public TreeData(String text, List<TreeData> nodes) {
        this.text = text;
        this.nodes = nodes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<TreeData> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeData> nodes) {
        this.nodes = nodes;
    }
}
