package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/memos")
public class MemoController {

    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    // 一覧表示
    @GetMapping
    public String index(Model model) {
        model.addAttribute("memos", memoService.findAll());
        return "memos/index";
    }

    // 新規作成フォーム表示
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("memo", new Memo());
        return "memos/form";
    }

    // 新規作成フォーム送信
    @PostMapping
    public String create(@ModelAttribute Memo memo) {
        memoService.save(memo);
        return "redirect:/memos";
    }

    // 編集フォーム表示
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("memo", memoService.findById(id));
        return "memos/form";
    }

    // 編集フォーム送信
    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Memo memo) {
        memo.setId(id);
        memoService.save(memo);
        return "redirect:/memos";
    }

    // 削除
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        memoService.deleteById(id);
        return "redirect:/memos";
    }
}