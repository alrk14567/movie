package com.nc13.movie.service;

import com.nc13.movie.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final int PAGE_SIZE=20;
    @Autowired
    private final SqlSession session;

    private final String NAMESPACE="com.nc13.mappers.UserMapper";

    public UserDTO auth(UserDTO attempt) {
        return session.selectOne(NAMESPACE+".auth",attempt);
    }

    public boolean validateUsername(String username) {
        return session.selectOne(NAMESPACE + ".selectByUsername", username)==null;
    }

    public boolean validateNickname(String nickname) {
        return session.selectOne(NAMESPACE + ".selectByNickname", nickname)==null;
    }

    public void register(UserDTO attempt){
        session.insert(NAMESPACE+".register",attempt);
    }

    public UserDTO selectOne(int id) {
        return session.selectOne(NAMESPACE + ".selectOne", id);
    }


    public void update(UserDTO userDTO) {
        session.update(NAMESPACE + ".update", userDTO);
    }

    public void delete(int id) {
        session.delete(NAMESPACE + ".delete", id);
    }


    public UserDTO selectByUsername(String username) {
        return session.selectOne(NAMESPACE+".selectByUsername",username);
    }

    public List<UserDTO> selectAll(int pageNo) {
        HashMap<String, Integer> paramMap = new HashMap<>();
        paramMap.put("startRow", (pageNo - 1) * PAGE_SIZE);
        paramMap.put("size", PAGE_SIZE);

        return session.selectList(NAMESPACE + ".selectAll", pageNo);
    }

    public List<UserDTO> selectSearch(int pageNo, String inputNickname) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("startRow", (pageNo - 1) * PAGE_SIZE);
        paramMap.put("size", PAGE_SIZE);
        paramMap.put("inputNickname", inputNickname);


        return session.selectList(NAMESPACE + ".selectSearch", paramMap);
    }

    public int selectMaxPage() {
        // 글의 총 갯수
        int maxRow = session.selectOne(NAMESPACE + ".selectMaxRow");

        // 총 페이지 갯수
        int maxPage = maxRow / PAGE_SIZE;

        if (maxRow % PAGE_SIZE != 0) {
            maxPage++;
        }

        return maxPage;
    }

    public int selectMaxPageSearch(String inputNickname) {
        int maxRow = session.selectOne(NAMESPACE + ".selectMaxRowSearch", inputNickname);
        int maxPage = maxRow / PAGE_SIZE;

        if (maxRow % PAGE_SIZE != 0) {
            maxPage++;
        }

        return maxPage;
    }
}
