package xyz.luan.monstros.domain.company;

import io.yawp.repository.IdRef;
import io.yawp.repository.annotations.Endpoint;
import io.yawp.repository.annotations.Id;
import io.yawp.repository.annotations.Index;
import io.yawp.repository.annotations.Json;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Endpoint(path = "/companies")
public class Company {

	@Id
	private IdRef<Company> id;

	private String name;

	@Index
	private String domain;

	@Json
	private List<Model> models = new ArrayList<>();
}
