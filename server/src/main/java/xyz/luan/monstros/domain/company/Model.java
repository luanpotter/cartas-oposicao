package xyz.luan.monstros.domain.company;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Model {

	private String fileName;

	private String fileId;

	private List<String> customParameterNames = new ArrayList<>();
}
