package com.nc13.movie.controller;

import com.nc13.movie.model.MovieDTO;
import com.nc13.movie.model.UserDTO;
import com.nc13.movie.service.MovieService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/movie/")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("showAll")
    public String moveToFirstPage() {
        return "redirect:/movie/showAll/1";
    }

    @PostMapping("showAll")
    public String searchResult(HttpSession session, String inputSearch) {
        String checkInput = inputSearch;
        session.setAttribute("inputSearch", checkInput);

        return "redirect:/movie/showAll/1";
    }

    @GetMapping("showAll/{pageNo}")
    public String showAll(HttpSession session, Authentication authentication, Model model, @PathVariable int pageNo) {
        UserDTO logIn = (UserDTO) authentication.getPrincipal();
        if (logIn == null) {
            return "redirect:/";
        }

        String inputSearch = (String) session.getAttribute("inputSearch");
        model.addAttribute("inputSearch", inputSearch);

        int maxPage;
        if (inputSearch != null) {
            int checkPage = movieService.selectMaxPageSearch(inputSearch);
            maxPage=checkPage;
            model.addAttribute("maxPage",maxPage);
        } else {
            maxPage= movieService.selectMaxPage();
            model.addAttribute("maxPage",maxPage);
        }

        int startPage;
        int endPage;

        if (maxPage < 5) {
            startPage = 1;
            endPage = maxPage;
        } else if (pageNo <= 3) {
            startPage = 1;
            endPage = 5;
        } else if (pageNo >= maxPage - 2) {
            startPage = maxPage - 4;
            endPage = maxPage;
        } else {
            startPage = pageNo - 2;
            endPage = pageNo + 2;
        }

        model.addAttribute("curPage", pageNo);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        if(inputSearch!=null) {
            List<MovieDTO> list = movieService.selectSearch(pageNo,inputSearch);
            model.addAttribute("list",list);
        }else {
            List<MovieDTO> list = movieService.selectAll(pageNo);
            model.addAttribute("list", list);
        }

        String path = "/movie/uploads/";
        model.addAttribute("path",path);

        return "movie/showAll";

    }

    @GetMapping("write")
    public String showWrite(Authentication authentication, Model model) {
        UserDTO logIn = (UserDTO) authentication.getPrincipal();
        if (logIn == null) {
            return "redirect:/";
        }

        return "movie/write";
    }

    @PostMapping("write")
    public String write(HttpSession session, MovieDTO movieDTO, @RequestParam("file") MultipartFile file, Model model, MultipartHttpServletRequest request) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }
        movieService.insert(movieDTO);
        int productId = movieService.selectMoiveId();

        if (!file.isEmpty()) {
            try {
                //파일 업로드 경로 설정 및 만들기
                String path = "src/main/webapp/movie/uploads/" + productId;
                Path uploadPath = Paths.get(path);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                // 파일 이름 만들기
                String fileName = file.getOriginalFilename();
                String extension = fileName.substring(fileName.lastIndexOf("."));
                String uploadName = fileName + extension;

                // 파일 저장하기
                Path filePath = uploadPath.resolve(uploadName);
                Files.copy(file.getInputStream(), filePath);

                // DB에 파일 저장할 경로 넣기
                movieDTO.setFileName(uploadName);

            } catch (IOException e) {
                e.printStackTrace();
                return " redirect:/movie/showAll";
            }
        }


        movieService.updateFileName(movieDTO);  //=> 파일 네임이 여기서 DB에 인풋


        return "redirect:/movie/showOne/" + movieDTO.getId();
    }

    @GetMapping("showOne/{id}")
    public String showOne(Authentication authentication, @PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        UserDTO logIn = (UserDTO) authentication.getPrincipal();
        if (logIn == null) {
            return "redirect:/";
        }
        MovieDTO movieDTO = movieService.selectOne(id);

        if (movieDTO == null) {
            redirectAttributes.addFlashAttribute("message", "해당 영화는 유효하지 않습니다.");
            return "redirect:/showMessage";
        }

        // List<ReplyDTO> replyList = replyService.selectAll(id);

        model.addAttribute("movieDTO", movieDTO);
        //model.addAttribute("replyList",replyList);

        return "movie/showOne";
    }

    @GetMapping("update/{id}")
    public String showUpdate(@PathVariable int id, Authentication authentication, RedirectAttributes redirectAttributes, Model model) {
        UserDTO logIn = (UserDTO) authentication.getPrincipal();
        if (logIn == null) {
            return "redirect:/";
        }

        MovieDTO movieDTO=movieService.selectOne(id);

        if (movieDTO == null) {
            redirectAttributes.addFlashAttribute("message", "해당 영화는 유효하지 않습니다.");
            return "redirect:/showMessage";
        }

        if (!logIn.getRole().equals("ADMIN")) {
            redirectAttributes.addFlashAttribute("message", "권한이 없습니다.");
            return "redirect:/showMessage";
        }
        model.addAttribute("movieDTO", movieDTO);
        return "movie/update";
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable int id, Authentication authentication, RedirectAttributes redirectAttributes, MovieDTO attempt) {
        UserDTO logIn = (UserDTO) authentication.getPrincipal();
        if (logIn == null) {
            return "redirect:/";
        }

       MovieDTO movieDTO = movieService.selectOne(id);
        if (movieDTO == null) {
            redirectAttributes.addFlashAttribute("message", "해당 영화는 유효하지 않습니다.");
            return "redirect:/showMessage";
        }

        if (!logIn.getRole().equals("ADMIN")) {
            redirectAttributes.addFlashAttribute("message", "권한이 없습니다.");
            return "redirect:/showMessage";
        }

        attempt.setId(id);

        movieService.update(attempt);

        return "redirect:/movie/showOne" + id;
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable int id, Authentication authentication, RedirectAttributes redirectAttributes) {
        UserDTO logIn = (UserDTO) authentication.getPrincipal();
        if (logIn == null) {
            return "redirect:/";
        }

        MovieDTO movieDTO = movieService.selectOne(id);
        if (movieDTO == null) {
            redirectAttributes.addFlashAttribute("message", "해당 영화는 유효하지 않습니다.");
            return "redirect:/showMessage";
        }

        if (!logIn.getRole().equals("ADMIN")) {
            redirectAttributes.addFlashAttribute("message", "권한이 없습니다.");
            return "redirect:/showMessage";
        }

        movieService.delete(id);

        return "redirect:/movie/showAll";
    }

    @ResponseBody
    @PostMapping("uploads")
    public Map<String, Object> uploads(MultipartHttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();

        String uploadPath = "";

        MultipartFile file = request.getFile("upload");
        String filName = file.getOriginalFilename();
        String extension = filName.substring(filName.lastIndexOf("."));
        String uploadName = UUID.randomUUID() + extension;

        String realPath = request.getServletContext().getRealPath("/movie/uploads/");
        Path realDir = Paths.get(realPath);
        if (!Files.exists(realDir)) {
            try {
                Files.createDirectories(realDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File uploadFile = new File(realPath + uploadName);
        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }


        uploadPath = "/movie/uploads/" + uploadName;

        resultMap.put("uploaded", true);
        resultMap.put("url", uploadPath);
        return resultMap;
    }
}
