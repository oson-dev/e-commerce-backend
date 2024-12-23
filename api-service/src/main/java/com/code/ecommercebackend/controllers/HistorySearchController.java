package com.code.ecommercebackend.controllers;

import com.code.ecommercebackend.dtos.response.Response;
import com.code.ecommercebackend.dtos.response.ResponseSuccess;
import com.code.ecommercebackend.services.historySearch.HistorySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/history-search")
@RequiredArgsConstructor
public class HistorySearchController {
    private final HistorySearchService historySearchService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public Response getHistorySearch(@RequestParam(required = false) String userId,
                                     @RequestParam(required = false) String content) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                historySearchService.getHistorySearch(userId, content)
        );
    }
    @DeleteMapping("/{id}")
    public Response deleteHistorySearch(@PathVariable String id
                                     ) {
        historySearchService.deleteHistorySearchById(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success"

        );
    }
}
