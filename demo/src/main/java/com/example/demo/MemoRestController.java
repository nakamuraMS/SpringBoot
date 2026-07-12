package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/memos")
public class MemoRestController {

    private final MemoService memoService;

    public MemoRestController(MemoService memoService) {
        this.memoService = memoService;
    }

    // 全件取得 GET /api/memos
    @GetMapping
    public List<Memo> getAll() {
        return memoService.findAll();
    }

    // 1件取得 GET /api/memos/{id}
    @GetMapping("/{id}")
    public Memo getOne(@PathVariable Long id) {
        return memoService.findById(id);
    }

    // 新規作成 POST /api/memos
    @PostMapping
    public ResponseEntity<Memo> create(@Valid @RequestBody Memo memo) {
        memoService.save(memo);
        return ResponseEntity.status(HttpStatus.CREATED).body(memo);
    }

    // 更新 PUT /api/memos/{id}
    @PutMapping("/{id}")
    public Memo update(@PathVariable Long id, @Valid @RequestBody Memo memo) {
        memo.setId(id);
        memoService.save(memo);
        return memo;
    }

    // 削除 DELETE /api/memos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}