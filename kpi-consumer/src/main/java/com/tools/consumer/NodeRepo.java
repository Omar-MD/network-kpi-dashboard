package com.tools.consumer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRepo extends JpaRepository<NodeData, Integer> {

}
