package com.nc13.movie.service;

import com.nc13.movie.model.GradeDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class GradeService {
    private final String NAMESPACE="com.nc13.mappers.GradeMapper";

    @Autowired
    private SqlSession session;

    // 해당 영화에 평가된 리뷰 불러오기
    public List<GradeDTO> selectAll(int movieId) {
        return session.selectList(NAMESPACE+".selectAll",movieId);
    }

    public void insert(GradeDTO gradeDTO) {
        session.insert(NAMESPACE+".insert",gradeDTO);
    }

    public void update(GradeDTO gradeDTO) {
        session.update(NAMESPACE+".update",gradeDTO);
    }

    public void delete (int id) {
        session.delete(NAMESPACE+".delete",id);
    }

    public GradeDTO gradeCheck(int id, int movieId) {
        HashMap<String, Integer> paramMap=new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("movieId",movieId);
        return session.selectOne(NAMESPACE+".gradeCheck",paramMap);
    }

    public double gradeAll(int movieId){
        return session.selectOne(NAMESPACE+"gradeAll",movieId);
    }

    public double gradeRank2(int movieId) {
        return session.selectOne(NAMESPACE+"gradeRank2",movieId);
    }

    public double gradeRank3(int movieId) {
        return session.selectOne(NAMESPACE+"gradeRank3",movieId);
    }
}
