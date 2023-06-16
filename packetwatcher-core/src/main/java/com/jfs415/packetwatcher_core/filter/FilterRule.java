package com.jfs415.packetwatcher_core.filter;

/**
 * This enum denotes the different filtering rules available to implement.
 * Ingress filtering specifically targets packets incoming to the network.
 * Egress filtering specifically targets packets leaving the network.
 */
public enum FilterRule {
	INGRESS,
	EGRESS;
}
