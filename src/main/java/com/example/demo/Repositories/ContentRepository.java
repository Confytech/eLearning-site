package com.example.demo.Repositories;


import com.example.demo.Models.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
