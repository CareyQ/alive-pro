package com.careyq.alive.module.infra.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.careyq.alive.core.domain.Result;
import com.careyq.alive.module.infra.dto.*;
import com.careyq.alive.module.infra.service.FileService;
import com.careyq.alive.module.infra.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 文件
 *
 * @author CareyQ
 */
@RestController
@AllArgsConstructor
@RequestMapping("/infra/file")
@Tag(name = "管理后台 - 文件")
public class FileController {

    private final FileService fileService;

    @PostMapping("/save")
    @Operation(summary = "保存文件")
    public Result<Long> saveFile(@Validated @RequestBody FileDTO dto) {
        return Result.ok(fileService.saveFile(dto));
    }

    @PostMapping("/page")
    @Operation(summary = "获取文件分页")
    public Result<IPage<FilePageVO>> getFilePage(@Validated @RequestBody FilePageDTO dto) {
        return Result.ok(fileService.getFilePage(dto));
    }

    @GetMapping("/detail")
    @Operation(summary = "获取文件详情")
    public Result<FileVO> getFileDetail(@RequestParam Long id) {
        return Result.ok(fileService.getFileDetail(id));
    }

    @DeleteMapping("/del")
    @Operation(summary = "删除文件")
    public Result<Boolean> delFile(@RequestParam Long id) {
        fileService.delFile(id);
        return Result.ok(true);
    }
}
