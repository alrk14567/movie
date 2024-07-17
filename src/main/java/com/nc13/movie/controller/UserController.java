package com.nc13.movie.controller;

import com.nc13.movie.model.UserDTO;
import com.nc13.movie.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user/")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("showMenu")
    public String showMenu(Authentication authentication, Model model) {

        UserDTO logIn = (UserDTO) authentication.getPrincipal();
        model.addAttribute("logIn", logIn);

        return "user/showMenu";
    }

    @PostMapping("auth")
    public String auth(UserDTO userDTO, HttpSession session) {
        UserDTO result = userService.auth(userDTO);
        if (result != null) {
            session.setAttribute("logIn", result);
            return "redirect:/user/showMenu";
        }

        return "redirect:/";
    }

    @GetMapping("register")
    public String showRegister() {
        return "user/register";
    }

    @PostMapping("register")
    public String register(UserDTO userDTO, RedirectAttributes redirectAttributes) {
        if (userService.validateUsername(userDTO.getUsername())) {
            userDTO.setPassword(encoder.encode(userDTO.getPassword()));
            userService.register(userDTO);
            System.out.println("회원가입 성공!!");
        } else {
            redirectAttributes.addFlashAttribute("message", "중복된 아이디로는 가입하실 수 없습니다.");
            return "redirect:/showMessage";
        }
        return "redirect:/";
    }

    @GetMapping("memberList")
    public String moveToFirstPage() {
        return "redirect:/user/memberList/1";
    }

    @PostMapping("memberList")
    public String searchResult(HttpSession session, String  inputNickname) {
        String check=inputNickname;
        session.setAttribute("inputNickname",check);
        return "redirect:/user/memberList/1";
    }

    @GetMapping("memberList/{pageNo}")
    public String showList(Authentication authentication, HttpSession session, Model model, @PathVariable int pageNo) {
        UserDTO logIn = (UserDTO) authentication.getPrincipal();
        if (logIn == null) {
            return "redirect:/";
        }

        String inputNickname = (String) session.getAttribute("inputNickname");
        model.addAttribute("inputNickname", inputNickname);

        int maxPage;
        if (inputNickname != null) {
            int checkPage = userService.selectMaxPageSearch(inputNickname);
            maxPage = checkPage;

        } else {
            maxPage = userService.selectMaxPage();
        }

        model.addAttribute("maxPage", maxPage);

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

        if (inputNickname != null) {
            List<UserDTO> list = userService.selectSearch(pageNo, inputNickname);
            model.addAttribute("list", list);
        } else {
            List<UserDTO> list = userService.selectAll(pageNo);
            model.addAttribute("list", list);
        }
        return "user/memberList";
    }

    @GetMapping("memberOne/{id}")
    public String memberOne(@PathVariable int id, RedirectAttributes redirectAttributes, Model model, Authentication authentication) {
        UserDTO logIn = (UserDTO) authentication.getPrincipal();
        if (logIn == null) {
            return "redirect:/";
        } else if (!logIn.getRole().equals("ADMIN")) {
            redirectAttributes.addFlashAttribute("message", "권한 없음");
            return "redirect:/showMessage";
        }

        UserDTO userDTO = userService.selectOne(id);
        if (userDTO == null) {
            redirectAttributes.addFlashAttribute("message", " 회원 정보 없음");
            return "redirect:/showMessage";
        }
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("logIn",logIn);

        return "user/memberOne";
    }

    @PostMapping("roleUpdate/{id}")
    public String roleUpdate(@PathVariable int id, RedirectAttributes redirectAttributes, Model model, Authentication authentication, UserDTO attempt) {
        UserDTO logIn = (UserDTO) authentication.getPrincipal();
        if (logIn == null) {
            return "redirect:/";
        }
        UserDTO userDTO = userService.selectOne(id);
        if (userDTO == null) {
            redirectAttributes.addFlashAttribute("message", " 회원 정보 없음");
            return "redirect:/showMessage";
        }
        if (userDTO.getId() != logIn.getId() && !(logIn.getRole().equals("ADMIN"))) {
            redirectAttributes.addFlashAttribute("message", "권한이 없습니다.");
            return "redirect:/showMessage";
        }
        userService.update(attempt);
        model.addAttribute("userDTO", userDTO);
        return "redirect:/user/memberOne" + id;
    }

    @GetMapping("update/{id}")
    public String memberUpdate(@PathVariable int id, RedirectAttributes redirectAttributes, Model model, Authentication authentication) {
        UserDTO logIn = (UserDTO) authentication.getPrincipal();
        if (logIn == null) {
            return "redirect:/";
        }

        UserDTO userDTO = userService.selectOne(id);
        if (userDTO == null) {
            redirectAttributes.addFlashAttribute("message", " 회원 정보 없음");
            return "redirect:/showMessage";
        }
        if (userDTO.getId() != logIn.getId() && !(logIn.getRole().equals("ADMIN"))) {
            redirectAttributes.addFlashAttribute("message", "권한이 없습니다.");
            return "redirect:/showMessage";
        }

        model.addAttribute("userDTO", userDTO);
        return "user/update";
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable int id, Authentication authentication, RedirectAttributes redirectAttributes, UserDTO attempt) {
        UserDTO logIn = (UserDTO) authentication.getPrincipal();
        if (logIn == null) {
            return "redirect:/";
        }

        UserDTO userDTO = userService.selectOne(id);
        if (userDTO == null) {
            redirectAttributes.addFlashAttribute("message", "존재하지 않는 회원 정보");
            return "redirect:/showMessage";
        }

        if (userDTO.getId() != logIn.getId() || !logIn.getRole().equals("ADMIN")) {
            redirectAttributes.addFlashAttribute("message", "권한 없음");
            return "redirect:/showMessage";
        }
        attempt.setId(id);

        userService.update(attempt);

        return "redirect:/user/memberOne" + id;
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable int id, Authentication authentication,RedirectAttributes redirectAttributes) {
        UserDTO logIn=(UserDTO) authentication.getPrincipal();
        if (logIn == null) {
            return "redirect:/";
        }

        UserDTO userDTO = userService.selectOne(id);
        if (userDTO == null) {
            redirectAttributes.addFlashAttribute("message", "존재하지 않는 회원 정보");
            return "redirect:/showMessage";
        }

        if (userDTO.getId() != logIn.getId() || !logIn.getRole().equals("ADMIN")) {
            redirectAttributes.addFlashAttribute("message", "권한 없음");
            return "redirect:/showMessage";
        }

        userService.delete(id);

        return "redirect:/user/memberAll";
    }

   /* @GetMapping("logOut")
    public String logOut(Authentication authentication) {
        authentication= SecurityContextHolder.getContext().getAuthentication();

        if (authentication !=  null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        return "redirect:/";
    }*/

}
