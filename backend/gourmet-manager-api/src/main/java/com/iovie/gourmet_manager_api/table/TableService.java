package com.iovie.gourmet_manager_api.table;

import com.iovie.gourmet_manager_api.table.dto.TableCreateRequest;
import com.iovie.gourmet_manager_api.table.dto.TableMapper;
import com.iovie.gourmet_manager_api.table.dto.TableUpdateRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TableService {

    private final TableRepository tableRepository;
    private final TableMapper tableMapper;

    public RestaurantTable getById(Long id) {
        return tableRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Table not found"));
    }

    public List<RestaurantTable> getAll() {
        return tableRepository.findAll();
    }

    public RestaurantTable create(TableCreateRequest tableCreateRequest) {
        if (tableRepository.existsByNumber(tableCreateRequest.getNumber())) {
            throw new IllegalArgumentException("Table number already in use");
        }
        return tableRepository.save(tableMapper.toTable(tableCreateRequest));
    }

    public RestaurantTable update(Long id, TableUpdateRequest tableUpdateRequest) {
        RestaurantTable table = tableRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Table not found"));
        if (tableRepository.existsByNumber(tableUpdateRequest.getNumber())) {
            throw new IllegalArgumentException("Table number already in use");
        }
        tableMapper.updateTable(tableUpdateRequest, table);
        return tableRepository.save(table);
    }

    public RestaurantTable updateStatus(Long id, TableStatus status) {
        RestaurantTable table = tableRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Table not found"));
        table.setStatus(status);
        return tableRepository.save(table);
    }


}

/*
GET    /api/tables                    → todas as mesas (ADMIN, MANAGER)
GET    /api/tables/{id}               → mesa por id
GET    /api/tables/available          → mesas disponíveis
GET    /api/tables/available?capacity=4 → mesas disponíveis com capacidade mínima
POST   /api/tables                    → criar mesa (ADMIN)
PUT    /api/tables/{id}               → actualizar capacidade (ADMIN)
PATCH  /api/tables/{id}/status        → mudar status
*/