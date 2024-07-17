package com.nc13.movie.service;


import com.nc13.movie.model.TheaterDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TheaterService {
    private final String NAMESPACE="com.nc13.mappers.TheaterMapper";

    private final int PAGE_SIZE=15;

    @Autowired
    private SqlSession session;

    public List<TheaterDTO> selectAll(int pageNo) {
        HashMap<String, Integer> paramMap=new HashMap<>();
        paramMap.put("startRow",(pageNo-1)*PAGE_SIZE);
        paramMap.put("size",PAGE_SIZE);

        return session.selectList(NAMESPACE+ ".selectAll",paramMap);
    }

    public List<TheaterDTO> selectSearch(int pageNo, String inputSearch) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("startRow", (pageNo-1)*PAGE_SIZE);
        paramMap.put("size",PAGE_SIZE);
        paramMap.put("inputSearch", inputSearch);

        return session.selectList(NAMESPACE+".selectSearch",paramMap);
    }

    public void insert(TheaterDTO theaterDTO){

        session.insert(NAMESPACE+ ".insert",theaterDTO);
    }

    public TheaterDTO selectOne(int id) {

        return session.selectOne(NAMESPACE + ".selectOne",id);
    }

    public void update(TheaterDTO attempt) {

        session.update(NAMESPACE+".update",attempt);
    }

    public void updateFileName(TheaterDTO attempt) {
        session.update(NAMESPACE+".updateFileName",attempt);    }

    public void delete(int id) {

        session.delete(NAMESPACE +".delete",id);
    }

    public int selectMaxPage() {
        int maxRow=session.selectOne(NAMESPACE+ ".selectMaxRow");
        int maxPage=maxRow/PAGE_SIZE;

        if(maxRow%PAGE_SIZE !=0) {
            maxPage++;
        }
        return maxPage;
    }

    public int selectMaxPageSearch(String inputSearch) {
        HashMap<String,Object> paramMap=new HashMap<>();
        paramMap.put("inputSearch",inputSearch);
        int maxRow = session.selectOne(NAMESPACE + ".selectMaxRowSearch", paramMap);
        int maxPage = maxRow / PAGE_SIZE;

        if (maxRow % PAGE_SIZE != 0) {
            maxPage++;
        }

        return maxPage;
    }

    public int selectTheaterId() {
        return session.selectOne(NAMESPACE+".selectWrite");
    }
}
