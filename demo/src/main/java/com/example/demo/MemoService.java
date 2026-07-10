package com.example.demo;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MemoService {

    private final MemoRepository memoRepository;

    // コンストラクタインジェクション
    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    // 全件取得
    public List<Memo> findAll() {
        return memoRepository.findAll();
    }

    // IDで1件取得
    public Memo findById(Long id) {
        return memoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Memo not found: " + id));
    }

    // 保存（新規・更新どちらも）
    public void save(Memo memo) {
        memoRepository.save(memo);
    }

    // 削除
    public void deleteById(Long id) {
        memoRepository.deleteById(id);
    }
}