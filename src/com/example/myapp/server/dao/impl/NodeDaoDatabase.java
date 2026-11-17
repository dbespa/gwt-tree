package com.example.myapp.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.example.myapp.server.dao.NodeDao;
import com.example.myapp.server.entity.NodeEntity;
import com.example.myapp.server.mapper.NodeMapper;

public class NodeDaoDatabase implements NodeDao {

    private SqlSessionFactory sqlSessionFactory;

    public NodeDaoDatabase(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public List<NodeEntity> getAllNodes() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            List<NodeEntity> allNodes = mapper.getAllNodes();
            return allNodes;
        }
    }

    @Override
    public NodeEntity getNodeById(Long id) {
        if (id == null) {
            return null;
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            NodeEntity node = mapper.getNodeById(id);
            return node;
        }
    }

    @Override
    public NodeEntity save(NodeEntity node) {
        if (node == null) {
            return null;
        }

        if (node.getId() == null) {
            return addNewNode(node);
        }

        return editNode(node);
    }

    private NodeEntity addNewNode(NodeEntity newNode) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            mapper.addNewNode(newNode);
            session.commit();
            return getNodeById(newNode.getId());
        }
    }

    private NodeEntity editNode(NodeEntity newNode) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            mapper.editNode(newNode);
            session.commit();
            return getNodeById(newNode.getId());
        }
    }

    @Override
    public NodeEntity deleteNode(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            NodeEntity node = getNodeById(id);
            mapper.deleteNode(id);
            session.commit();
            return node;
        }
    }
}
