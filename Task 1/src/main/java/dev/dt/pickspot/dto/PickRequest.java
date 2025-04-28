package dev.dt.pickspot.dto;

import java.util.List;
import dev.dt.pickspot.model.Container;
import dev.dt.pickspot.model.Slot;

public record PickRequest(Container container, List<Slot> yardMap) {}
