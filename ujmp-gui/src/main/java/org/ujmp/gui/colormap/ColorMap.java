/*
 * Copyright (C) 2008-2014 by Holger Arndt
 *
 * This file is part of the Universal Java Matrix Package (UJMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * UJMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * UJMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with UJMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.ujmp.gui.colormap;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ColorMap {

	private List<Color> colors = null;

	public static final Color[] colorBlackToGreen = { new Color(0, 0, 0), new Color(0, 1, 0), new Color(0, 2, 0),
			new Color(0, 3, 0), new Color(0, 4, 0), new Color(0, 5, 0), new Color(0, 6, 0), new Color(0, 7, 0),
			new Color(0, 8, 0), new Color(0, 9, 0), new Color(0, 10, 0), new Color(0, 11, 0), new Color(0, 12, 0),
			new Color(0, 13, 0), new Color(0, 14, 0), new Color(0, 15, 0), new Color(0, 16, 0), new Color(0, 17, 0),
			new Color(0, 18, 0), new Color(0, 19, 0), new Color(0, 20, 0), new Color(0, 21, 0), new Color(0, 22, 0),
			new Color(0, 23, 0), new Color(0, 24, 0), new Color(0, 25, 0), new Color(0, 26, 0), new Color(0, 27, 0),
			new Color(0, 28, 0), new Color(0, 29, 0), new Color(0, 30, 0), new Color(0, 31, 0), new Color(0, 32, 0),
			new Color(0, 33, 0), new Color(0, 34, 0), new Color(0, 35, 0), new Color(0, 36, 0), new Color(0, 37, 0),
			new Color(0, 38, 0), new Color(0, 39, 0), new Color(0, 40, 0), new Color(0, 41, 0), new Color(0, 42, 0),
			new Color(0, 43, 0), new Color(0, 44, 0), new Color(0, 45, 0), new Color(0, 46, 0), new Color(0, 47, 0),
			new Color(0, 48, 0), new Color(0, 49, 0), new Color(0, 50, 0), new Color(0, 51, 0), new Color(0, 52, 0),
			new Color(0, 53, 0), new Color(0, 54, 0), new Color(0, 55, 0), new Color(0, 56, 0), new Color(0, 57, 0),
			new Color(0, 58, 0), new Color(0, 59, 0), new Color(0, 60, 0), new Color(0, 61, 0), new Color(0, 62, 0),
			new Color(0, 63, 0), new Color(0, 64, 0), new Color(0, 65, 0), new Color(0, 66, 0), new Color(0, 67, 0),
			new Color(0, 68, 0), new Color(0, 69, 0), new Color(0, 70, 0), new Color(0, 71, 0), new Color(0, 72, 0),
			new Color(0, 73, 0), new Color(0, 74, 0), new Color(0, 75, 0), new Color(0, 76, 0), new Color(0, 77, 0),
			new Color(0, 78, 0), new Color(0, 79, 0), new Color(0, 80, 0), new Color(0, 81, 0), new Color(0, 82, 0),
			new Color(0, 83, 0), new Color(0, 84, 0), new Color(0, 85, 0), new Color(0, 86, 0), new Color(0, 87, 0),
			new Color(0, 88, 0), new Color(0, 89, 0), new Color(0, 90, 0), new Color(0, 91, 0), new Color(0, 92, 0),
			new Color(0, 93, 0), new Color(0, 94, 0), new Color(0, 95, 0), new Color(0, 96, 0), new Color(0, 97, 0),
			new Color(0, 98, 0), new Color(0, 99, 0), new Color(0, 100, 0), new Color(0, 101, 0), new Color(0, 102, 0),
			new Color(0, 103, 0), new Color(0, 104, 0), new Color(0, 105, 0), new Color(0, 106, 0),
			new Color(0, 107, 0), new Color(0, 108, 0), new Color(0, 109, 0), new Color(0, 110, 0),
			new Color(0, 111, 0), new Color(0, 112, 0), new Color(0, 113, 0), new Color(0, 114, 0),
			new Color(0, 115, 0), new Color(0, 116, 0), new Color(0, 117, 0), new Color(0, 118, 0),
			new Color(0, 119, 0), new Color(0, 120, 0), new Color(0, 121, 0), new Color(0, 122, 0),
			new Color(0, 123, 0), new Color(0, 124, 0), new Color(0, 125, 0), new Color(0, 126, 0),
			new Color(0, 127, 0), new Color(0, 128, 0), new Color(0, 129, 0), new Color(0, 130, 0),
			new Color(0, 131, 0), new Color(0, 132, 0), new Color(0, 133, 0), new Color(0, 134, 0),
			new Color(0, 135, 0), new Color(0, 136, 0), new Color(0, 137, 0), new Color(0, 138, 0),
			new Color(0, 139, 0), new Color(0, 140, 0), new Color(0, 141, 0), new Color(0, 142, 0),
			new Color(0, 143, 0), new Color(0, 144, 0), new Color(0, 145, 0), new Color(0, 146, 0),
			new Color(0, 147, 0), new Color(0, 148, 0), new Color(0, 149, 0), new Color(0, 150, 0),
			new Color(0, 151, 0), new Color(0, 152, 0), new Color(0, 153, 0), new Color(0, 154, 0),
			new Color(0, 155, 0), new Color(0, 156, 0), new Color(0, 157, 0), new Color(0, 158, 0),
			new Color(0, 159, 0), new Color(0, 160, 0), new Color(0, 161, 0), new Color(0, 162, 0),
			new Color(0, 163, 0), new Color(0, 164, 0), new Color(0, 165, 0), new Color(0, 166, 0),
			new Color(0, 167, 0), new Color(0, 168, 0), new Color(0, 169, 0), new Color(0, 170, 0),
			new Color(0, 171, 0), new Color(0, 172, 0), new Color(0, 173, 0), new Color(0, 174, 0),
			new Color(0, 175, 0), new Color(0, 176, 0), new Color(0, 177, 0), new Color(0, 178, 0),
			new Color(0, 179, 0), new Color(0, 180, 0), new Color(0, 181, 0), new Color(0, 182, 0),
			new Color(0, 183, 0), new Color(0, 184, 0), new Color(0, 185, 0), new Color(0, 186, 0),
			new Color(0, 187, 0), new Color(0, 188, 0), new Color(0, 189, 0), new Color(0, 190, 0),
			new Color(0, 191, 0), new Color(0, 192, 0), new Color(0, 193, 0), new Color(0, 194, 0),
			new Color(0, 195, 0), new Color(0, 196, 0), new Color(0, 197, 0), new Color(0, 198, 0),
			new Color(0, 199, 0), new Color(0, 200, 0), new Color(0, 201, 0), new Color(0, 202, 0),
			new Color(0, 203, 0), new Color(0, 204, 0), new Color(0, 205, 0), new Color(0, 206, 0),
			new Color(0, 207, 0), new Color(0, 208, 0), new Color(0, 209, 0), new Color(0, 210, 0),
			new Color(0, 211, 0), new Color(0, 212, 0), new Color(0, 213, 0), new Color(0, 214, 0),
			new Color(0, 215, 0), new Color(0, 216, 0), new Color(0, 217, 0), new Color(0, 218, 0),
			new Color(0, 219, 0), new Color(0, 220, 0), new Color(0, 221, 0), new Color(0, 222, 0),
			new Color(0, 223, 0), new Color(0, 224, 0), new Color(0, 225, 0), new Color(0, 226, 0),
			new Color(0, 227, 0), new Color(0, 228, 0), new Color(0, 229, 0), new Color(0, 230, 0),
			new Color(0, 231, 0), new Color(0, 232, 0), new Color(0, 233, 0), new Color(0, 234, 0),
			new Color(0, 235, 0), new Color(0, 236, 0), new Color(0, 237, 0), new Color(0, 238, 0),
			new Color(0, 239, 0), new Color(0, 240, 0), new Color(0, 241, 0), new Color(0, 242, 0),
			new Color(0, 243, 0), new Color(0, 244, 0), new Color(0, 245, 0), new Color(0, 246, 0),
			new Color(0, 247, 0), new Color(0, 248, 0), new Color(0, 249, 0), new Color(0, 250, 0),
			new Color(0, 251, 0), new Color(0, 252, 0), new Color(0, 253, 0), new Color(0, 254, 0),
			new Color(0, 255, 0) };

	public static final Color[] colorGreenToYellow = { new Color(0, 255, 0), new Color(1, 255, 0),
			new Color(2, 255, 0), new Color(3, 255, 0), new Color(4, 255, 0), new Color(5, 255, 0),
			new Color(6, 255, 0), new Color(7, 255, 0), new Color(8, 255, 0), new Color(9, 255, 0),
			new Color(10, 255, 0), new Color(11, 255, 0), new Color(12, 255, 0), new Color(13, 255, 0),
			new Color(14, 255, 0), new Color(15, 255, 0), new Color(16, 255, 0), new Color(17, 255, 0),
			new Color(18, 255, 0), new Color(19, 255, 0), new Color(20, 255, 0), new Color(21, 255, 0),
			new Color(22, 255, 0), new Color(23, 255, 0), new Color(24, 255, 0), new Color(25, 255, 0),
			new Color(26, 255, 0), new Color(27, 255, 0), new Color(28, 255, 0), new Color(29, 255, 0),
			new Color(30, 255, 0), new Color(31, 255, 0), new Color(32, 255, 0), new Color(33, 255, 0),
			new Color(34, 255, 0), new Color(35, 255, 0), new Color(36, 255, 0), new Color(37, 255, 0),
			new Color(38, 255, 0), new Color(39, 255, 0), new Color(40, 255, 0), new Color(41, 255, 0),
			new Color(42, 255, 0), new Color(43, 255, 0), new Color(44, 255, 0), new Color(45, 255, 0),
			new Color(46, 255, 0), new Color(47, 255, 0), new Color(48, 255, 0), new Color(49, 255, 0),
			new Color(50, 255, 0), new Color(51, 255, 0), new Color(52, 255, 0), new Color(53, 255, 0),
			new Color(54, 255, 0), new Color(55, 255, 0), new Color(56, 255, 0), new Color(57, 255, 0),
			new Color(58, 255, 0), new Color(59, 255, 0), new Color(60, 255, 0), new Color(61, 255, 0),
			new Color(62, 255, 0), new Color(63, 255, 0), new Color(64, 255, 0), new Color(65, 255, 0),
			new Color(66, 255, 0), new Color(67, 255, 0), new Color(68, 255, 0), new Color(69, 255, 0),
			new Color(70, 255, 0), new Color(71, 255, 0), new Color(72, 255, 0), new Color(73, 255, 0),
			new Color(74, 255, 0), new Color(75, 255, 0), new Color(76, 255, 0), new Color(77, 255, 0),
			new Color(78, 255, 0), new Color(79, 255, 0), new Color(80, 255, 0), new Color(81, 255, 0),
			new Color(82, 255, 0), new Color(83, 255, 0), new Color(84, 255, 0), new Color(85, 255, 0),
			new Color(86, 255, 0), new Color(87, 255, 0), new Color(88, 255, 0), new Color(89, 255, 0),
			new Color(90, 255, 0), new Color(91, 255, 0), new Color(92, 255, 0), new Color(93, 255, 0),
			new Color(94, 255, 0), new Color(95, 255, 0), new Color(96, 255, 0), new Color(97, 255, 0),
			new Color(98, 255, 0), new Color(99, 255, 0), new Color(100, 255, 0), new Color(101, 255, 0),
			new Color(102, 255, 0), new Color(103, 255, 0), new Color(104, 255, 0), new Color(105, 255, 0),
			new Color(106, 255, 0), new Color(107, 255, 0), new Color(108, 255, 0), new Color(109, 255, 0),
			new Color(110, 255, 0), new Color(111, 255, 0), new Color(112, 255, 0), new Color(113, 255, 0),
			new Color(114, 255, 0), new Color(115, 255, 0), new Color(116, 255, 0), new Color(117, 255, 0),
			new Color(118, 255, 0), new Color(119, 255, 0), new Color(120, 255, 0), new Color(121, 255, 0),
			new Color(122, 255, 0), new Color(123, 255, 0), new Color(124, 255, 0), new Color(125, 255, 0),
			new Color(126, 255, 0), new Color(127, 255, 0), new Color(128, 255, 0), new Color(129, 255, 0),
			new Color(130, 255, 0), new Color(131, 255, 0), new Color(132, 255, 0), new Color(133, 255, 0),
			new Color(134, 255, 0), new Color(135, 255, 0), new Color(136, 255, 0), new Color(137, 255, 0),
			new Color(138, 255, 0), new Color(139, 255, 0), new Color(140, 255, 0), new Color(141, 255, 0),
			new Color(142, 255, 0), new Color(143, 255, 0), new Color(144, 255, 0), new Color(145, 255, 0),
			new Color(146, 255, 0), new Color(147, 255, 0), new Color(148, 255, 0), new Color(149, 255, 0),
			new Color(150, 255, 0), new Color(151, 255, 0), new Color(152, 255, 0), new Color(153, 255, 0),
			new Color(154, 255, 0), new Color(155, 255, 0), new Color(156, 255, 0), new Color(157, 255, 0),
			new Color(158, 255, 0), new Color(159, 255, 0), new Color(160, 255, 0), new Color(161, 255, 0),
			new Color(162, 255, 0), new Color(163, 255, 0), new Color(164, 255, 0), new Color(165, 255, 0),
			new Color(166, 255, 0), new Color(167, 255, 0), new Color(168, 255, 0), new Color(169, 255, 0),
			new Color(170, 255, 0), new Color(171, 255, 0), new Color(172, 255, 0), new Color(173, 255, 0),
			new Color(174, 255, 0), new Color(175, 255, 0), new Color(176, 255, 0), new Color(177, 255, 0),
			new Color(178, 255, 0), new Color(179, 255, 0), new Color(180, 255, 0), new Color(181, 255, 0),
			new Color(182, 255, 0), new Color(183, 255, 0), new Color(184, 255, 0), new Color(185, 255, 0),
			new Color(186, 255, 0), new Color(187, 255, 0), new Color(188, 255, 0), new Color(189, 255, 0),
			new Color(190, 255, 0), new Color(191, 255, 0), new Color(192, 255, 0), new Color(193, 255, 0),
			new Color(194, 255, 0), new Color(195, 255, 0), new Color(196, 255, 0), new Color(197, 255, 0),
			new Color(198, 255, 0), new Color(199, 255, 0), new Color(200, 255, 0), new Color(201, 255, 0),
			new Color(202, 255, 0), new Color(203, 255, 0), new Color(204, 255, 0), new Color(205, 255, 0),
			new Color(206, 255, 0), new Color(207, 255, 0), new Color(208, 255, 0), new Color(209, 255, 0),
			new Color(210, 255, 0), new Color(211, 255, 0), new Color(212, 255, 0), new Color(213, 255, 0),
			new Color(214, 255, 0), new Color(215, 255, 0), new Color(216, 255, 0), new Color(217, 255, 0),
			new Color(218, 255, 0), new Color(219, 255, 0), new Color(220, 255, 0), new Color(221, 255, 0),
			new Color(222, 255, 0), new Color(223, 255, 0), new Color(224, 255, 0), new Color(225, 255, 0),
			new Color(226, 255, 0), new Color(227, 255, 0), new Color(228, 255, 0), new Color(229, 255, 0),
			new Color(230, 255, 0), new Color(231, 255, 0), new Color(232, 255, 0), new Color(233, 255, 0),
			new Color(234, 255, 0), new Color(235, 255, 0), new Color(236, 255, 0), new Color(237, 255, 0),
			new Color(238, 255, 0), new Color(239, 255, 0), new Color(240, 255, 0), new Color(241, 255, 0),
			new Color(242, 255, 0), new Color(243, 255, 0), new Color(244, 255, 0), new Color(245, 255, 0),
			new Color(246, 255, 0), new Color(247, 255, 0), new Color(248, 255, 0), new Color(249, 255, 0),
			new Color(250, 255, 0), new Color(251, 255, 0), new Color(252, 255, 0), new Color(253, 255, 0),
			new Color(254, 255, 0), new Color(255, 255, 0) };

	public static final Color[] colorRedToBlack = { new Color(255, 0, 0), new Color(254, 0, 0), new Color(253, 0, 0),
			new Color(252, 0, 0), new Color(251, 0, 0), new Color(250, 0, 0), new Color(249, 0, 0),
			new Color(248, 0, 0), new Color(247, 0, 0), new Color(246, 0, 0), new Color(245, 0, 0),
			new Color(244, 0, 0), new Color(243, 0, 0), new Color(242, 0, 0), new Color(241, 0, 0),
			new Color(240, 0, 0), new Color(239, 0, 0), new Color(238, 0, 0), new Color(237, 0, 0),
			new Color(236, 0, 0), new Color(235, 0, 0), new Color(234, 0, 0), new Color(233, 0, 0),
			new Color(232, 0, 0), new Color(231, 0, 0), new Color(230, 0, 0), new Color(229, 0, 0),
			new Color(228, 0, 0), new Color(227, 0, 0), new Color(226, 0, 0), new Color(225, 0, 0),
			new Color(224, 0, 0), new Color(223, 0, 0), new Color(222, 0, 0), new Color(221, 0, 0),
			new Color(220, 0, 0), new Color(219, 0, 0), new Color(218, 0, 0), new Color(217, 0, 0),
			new Color(216, 0, 0), new Color(215, 0, 0), new Color(214, 0, 0), new Color(213, 0, 0),
			new Color(212, 0, 0), new Color(211, 0, 0), new Color(210, 0, 0), new Color(209, 0, 0),
			new Color(208, 0, 0), new Color(207, 0, 0), new Color(206, 0, 0), new Color(205, 0, 0),
			new Color(204, 0, 0), new Color(203, 0, 0), new Color(202, 0, 0), new Color(201, 0, 0),
			new Color(200, 0, 0), new Color(199, 0, 0), new Color(198, 0, 0), new Color(197, 0, 0),
			new Color(196, 0, 0), new Color(195, 0, 0), new Color(194, 0, 0), new Color(193, 0, 0),
			new Color(192, 0, 0), new Color(191, 0, 0), new Color(190, 0, 0), new Color(189, 0, 0),
			new Color(188, 0, 0), new Color(187, 0, 0), new Color(186, 0, 0), new Color(185, 0, 0),
			new Color(184, 0, 0), new Color(183, 0, 0), new Color(182, 0, 0), new Color(181, 0, 0),
			new Color(180, 0, 0), new Color(179, 0, 0), new Color(178, 0, 0), new Color(177, 0, 0),
			new Color(176, 0, 0), new Color(175, 0, 0), new Color(174, 0, 0), new Color(173, 0, 0),
			new Color(172, 0, 0), new Color(171, 0, 0), new Color(170, 0, 0), new Color(169, 0, 0),
			new Color(168, 0, 0), new Color(167, 0, 0), new Color(166, 0, 0), new Color(165, 0, 0),
			new Color(164, 0, 0), new Color(163, 0, 0), new Color(162, 0, 0), new Color(161, 0, 0),
			new Color(160, 0, 0), new Color(159, 0, 0), new Color(158, 0, 0), new Color(157, 0, 0),
			new Color(156, 0, 0), new Color(155, 0, 0), new Color(154, 0, 0), new Color(153, 0, 0),
			new Color(152, 0, 0), new Color(151, 0, 0), new Color(150, 0, 0), new Color(149, 0, 0),
			new Color(148, 0, 0), new Color(147, 0, 0), new Color(146, 0, 0), new Color(145, 0, 0),
			new Color(144, 0, 0), new Color(143, 0, 0), new Color(142, 0, 0), new Color(141, 0, 0),
			new Color(140, 0, 0), new Color(139, 0, 0), new Color(138, 0, 0), new Color(137, 0, 0),
			new Color(136, 0, 0), new Color(135, 0, 0), new Color(134, 0, 0), new Color(133, 0, 0),
			new Color(132, 0, 0), new Color(131, 0, 0), new Color(130, 0, 0), new Color(129, 0, 0),
			new Color(128, 0, 0), new Color(127, 0, 0), new Color(126, 0, 0), new Color(125, 0, 0),
			new Color(124, 0, 0), new Color(123, 0, 0), new Color(122, 0, 0), new Color(121, 0, 0),
			new Color(120, 0, 0), new Color(119, 0, 0), new Color(118, 0, 0), new Color(117, 0, 0),
			new Color(116, 0, 0), new Color(115, 0, 0), new Color(114, 0, 0), new Color(113, 0, 0),
			new Color(112, 0, 0), new Color(111, 0, 0), new Color(110, 0, 0), new Color(109, 0, 0),
			new Color(108, 0, 0), new Color(107, 0, 0), new Color(106, 0, 0), new Color(105, 0, 0),
			new Color(104, 0, 0), new Color(103, 0, 0), new Color(102, 0, 0), new Color(101, 0, 0),
			new Color(100, 0, 0), new Color(99, 0, 0), new Color(98, 0, 0), new Color(97, 0, 0), new Color(96, 0, 0),
			new Color(95, 0, 0), new Color(94, 0, 0), new Color(93, 0, 0), new Color(92, 0, 0), new Color(91, 0, 0),
			new Color(90, 0, 0), new Color(89, 0, 0), new Color(88, 0, 0), new Color(87, 0, 0), new Color(86, 0, 0),
			new Color(85, 0, 0), new Color(84, 0, 0), new Color(83, 0, 0), new Color(82, 0, 0), new Color(81, 0, 0),
			new Color(80, 0, 0), new Color(79, 0, 0), new Color(78, 0, 0), new Color(77, 0, 0), new Color(76, 0, 0),
			new Color(75, 0, 0), new Color(74, 0, 0), new Color(73, 0, 0), new Color(72, 0, 0), new Color(71, 0, 0),
			new Color(70, 0, 0), new Color(69, 0, 0), new Color(68, 0, 0), new Color(67, 0, 0), new Color(66, 0, 0),
			new Color(65, 0, 0), new Color(64, 0, 0), new Color(63, 0, 0), new Color(62, 0, 0), new Color(61, 0, 0),
			new Color(60, 0, 0), new Color(59, 0, 0), new Color(58, 0, 0), new Color(57, 0, 0), new Color(56, 0, 0),
			new Color(55, 0, 0), new Color(54, 0, 0), new Color(53, 0, 0), new Color(52, 0, 0), new Color(51, 0, 0),
			new Color(50, 0, 0), new Color(49, 0, 0), new Color(48, 0, 0), new Color(47, 0, 0), new Color(46, 0, 0),
			new Color(45, 0, 0), new Color(44, 0, 0), new Color(43, 0, 0), new Color(42, 0, 0), new Color(41, 0, 0),
			new Color(40, 0, 0), new Color(39, 0, 0), new Color(38, 0, 0), new Color(37, 0, 0), new Color(36, 0, 0),
			new Color(35, 0, 0), new Color(34, 0, 0), new Color(33, 0, 0), new Color(32, 0, 0), new Color(31, 0, 0),
			new Color(30, 0, 0), new Color(29, 0, 0), new Color(28, 0, 0), new Color(27, 0, 0), new Color(26, 0, 0),
			new Color(25, 0, 0), new Color(24, 0, 0), new Color(23, 0, 0), new Color(22, 0, 0), new Color(21, 0, 0),
			new Color(20, 0, 0), new Color(19, 0, 0), new Color(18, 0, 0), new Color(17, 0, 0), new Color(16, 0, 0),
			new Color(15, 0, 0), new Color(14, 0, 0), new Color(13, 0, 0), new Color(12, 0, 0), new Color(11, 0, 0),
			new Color(10, 0, 0), new Color(9, 0, 0), new Color(8, 0, 0), new Color(7, 0, 0), new Color(6, 0, 0),
			new Color(5, 0, 0), new Color(4, 0, 0), new Color(3, 0, 0), new Color(2, 0, 0), new Color(1, 0, 0),
			new Color(0, 0, 0) };

	public static final Color[] defaultColors = { new Color(0, 0, 143), new Color(0, 0, 159), new Color(0, 0, 175),
			new Color(0, 0, 191), new Color(0, 0, 207), new Color(0, 0, 223), new Color(0, 0, 239),
			new Color(0, 0, 255), new Color(0, 16, 255), new Color(0, 32, 255), new Color(0, 48, 255),
			new Color(0, 64, 255), new Color(0, 80, 255), new Color(0, 96, 255), new Color(0, 112, 255),
			new Color(0, 128, 255), new Color(0, 143, 255), new Color(0, 159, 255), new Color(0, 175, 255),
			new Color(0, 191, 255), new Color(0, 207, 255), new Color(0, 223, 255), new Color(0, 239, 255),
			new Color(0, 255, 255), new Color(16, 255, 239), new Color(32, 255, 223), new Color(48, 255, 207),
			new Color(64, 255, 191), new Color(80, 255, 175), new Color(96, 255, 159), new Color(112, 255, 143),
			new Color(128, 255, 128), new Color(143, 255, 112), new Color(159, 255, 96), new Color(175, 255, 80),
			new Color(191, 255, 64), new Color(207, 255, 48), new Color(223, 255, 32), new Color(239, 255, 16),
			new Color(255, 255, 0), new Color(255, 239, 0), new Color(255, 223, 0), new Color(255, 207, 0),
			new Color(255, 191, 0), new Color(255, 175, 0), new Color(255, 159, 0), new Color(255, 143, 0),
			new Color(255, 128, 0), new Color(255, 112, 0), new Color(255, 96, 0), new Color(255, 80, 0),
			new Color(255, 64, 0), new Color(255, 48, 0), new Color(255, 32, 0), new Color(255, 16, 0),
			new Color(255, 0, 0), new Color(239, 0, 0), new Color(223, 0, 0), new Color(207, 0, 0),
			new Color(191, 0, 0), new Color(175, 0, 0), new Color(159, 0, 0), new Color(143, 0, 0),
			new Color(128, 0, 0) };

	public static final Color[] colorRedToMagenta = { new Color(255, 0, 0), new Color(255, 0, 1), new Color(255, 0, 2),
			new Color(255, 0, 3), new Color(255, 0, 4), new Color(255, 0, 5), new Color(255, 0, 6),
			new Color(255, 0, 7), new Color(255, 0, 8), new Color(255, 0, 9), new Color(255, 0, 10),
			new Color(255, 0, 11), new Color(255, 0, 12), new Color(255, 0, 13), new Color(255, 0, 14),
			new Color(255, 0, 15), new Color(255, 0, 16), new Color(255, 0, 17), new Color(255, 0, 18),
			new Color(255, 0, 19), new Color(255, 0, 20), new Color(255, 0, 21), new Color(255, 0, 22),
			new Color(255, 0, 23), new Color(255, 0, 24), new Color(255, 0, 25), new Color(255, 0, 26),
			new Color(255, 0, 27), new Color(255, 0, 28), new Color(255, 0, 29), new Color(255, 0, 30),
			new Color(255, 0, 31), new Color(255, 0, 32), new Color(255, 0, 33), new Color(255, 0, 34),
			new Color(255, 0, 35), new Color(255, 0, 36), new Color(255, 0, 37), new Color(255, 0, 38),
			new Color(255, 0, 39), new Color(255, 0, 40), new Color(255, 0, 41), new Color(255, 0, 42),
			new Color(255, 0, 43), new Color(255, 0, 44), new Color(255, 0, 45), new Color(255, 0, 46),
			new Color(255, 0, 47), new Color(255, 0, 48), new Color(255, 0, 49), new Color(255, 0, 50),
			new Color(255, 0, 51), new Color(255, 0, 52), new Color(255, 0, 53), new Color(255, 0, 54),
			new Color(255, 0, 55), new Color(255, 0, 56), new Color(255, 0, 57), new Color(255, 0, 58),
			new Color(255, 0, 59), new Color(255, 0, 60), new Color(255, 0, 61), new Color(255, 0, 62),
			new Color(255, 0, 63), new Color(255, 0, 64), new Color(255, 0, 65), new Color(255, 0, 66),
			new Color(255, 0, 67), new Color(255, 0, 68), new Color(255, 0, 69), new Color(255, 0, 70),
			new Color(255, 0, 71), new Color(255, 0, 72), new Color(255, 0, 73), new Color(255, 0, 74),
			new Color(255, 0, 75), new Color(255, 0, 76), new Color(255, 0, 77), new Color(255, 0, 78),
			new Color(255, 0, 79), new Color(255, 0, 80), new Color(255, 0, 81), new Color(255, 0, 82),
			new Color(255, 0, 83), new Color(255, 0, 84), new Color(255, 0, 85), new Color(255, 0, 86),
			new Color(255, 0, 87), new Color(255, 0, 88), new Color(255, 0, 89), new Color(255, 0, 90),
			new Color(255, 0, 91), new Color(255, 0, 92), new Color(255, 0, 93), new Color(255, 0, 94),
			new Color(255, 0, 95), new Color(255, 0, 96), new Color(255, 0, 97), new Color(255, 0, 98),
			new Color(255, 0, 99), new Color(255, 0, 100), new Color(255, 0, 101), new Color(255, 0, 102),
			new Color(255, 0, 103), new Color(255, 0, 104), new Color(255, 0, 105), new Color(255, 0, 106),
			new Color(255, 0, 107), new Color(255, 0, 108), new Color(255, 0, 109), new Color(255, 0, 110),
			new Color(255, 0, 111), new Color(255, 0, 112), new Color(255, 0, 113), new Color(255, 0, 114),
			new Color(255, 0, 115), new Color(255, 0, 116), new Color(255, 0, 117), new Color(255, 0, 118),
			new Color(255, 0, 119), new Color(255, 0, 120), new Color(255, 0, 121), new Color(255, 0, 122),
			new Color(255, 0, 123), new Color(255, 0, 124), new Color(255, 0, 125), new Color(255, 0, 126),
			new Color(255, 0, 127), new Color(255, 0, 128), new Color(255, 0, 129), new Color(255, 0, 130),
			new Color(255, 0, 131), new Color(255, 0, 132), new Color(255, 0, 133), new Color(255, 0, 134),
			new Color(255, 0, 135), new Color(255, 0, 136), new Color(255, 0, 137), new Color(255, 0, 138),
			new Color(255, 0, 139), new Color(255, 0, 140), new Color(255, 0, 141), new Color(255, 0, 142),
			new Color(255, 0, 143), new Color(255, 0, 144), new Color(255, 0, 145), new Color(255, 0, 146),
			new Color(255, 0, 147), new Color(255, 0, 148), new Color(255, 0, 149), new Color(255, 0, 150),
			new Color(255, 0, 151), new Color(255, 0, 152), new Color(255, 0, 153), new Color(255, 0, 154),
			new Color(255, 0, 155), new Color(255, 0, 156), new Color(255, 0, 157), new Color(255, 0, 158),
			new Color(255, 0, 159), new Color(255, 0, 160), new Color(255, 0, 161), new Color(255, 0, 162),
			new Color(255, 0, 163), new Color(255, 0, 164), new Color(255, 0, 165), new Color(255, 0, 166),
			new Color(255, 0, 167), new Color(255, 0, 168), new Color(255, 0, 169), new Color(255, 0, 170),
			new Color(255, 0, 171), new Color(255, 0, 172), new Color(255, 0, 173), new Color(255, 0, 174),
			new Color(255, 0, 175), new Color(255, 0, 176), new Color(255, 0, 177), new Color(255, 0, 178),
			new Color(255, 0, 179), new Color(255, 0, 180), new Color(255, 0, 181), new Color(255, 0, 182),
			new Color(255, 0, 183), new Color(255, 0, 184), new Color(255, 0, 185), new Color(255, 0, 186),
			new Color(255, 0, 187), new Color(255, 0, 188), new Color(255, 0, 189), new Color(255, 0, 190),
			new Color(255, 0, 191), new Color(255, 0, 192), new Color(255, 0, 193), new Color(255, 0, 194),
			new Color(255, 0, 195), new Color(255, 0, 196), new Color(255, 0, 197), new Color(255, 0, 198),
			new Color(255, 0, 199), new Color(255, 0, 200), new Color(255, 0, 201), new Color(255, 0, 202),
			new Color(255, 0, 203), new Color(255, 0, 204), new Color(255, 0, 205), new Color(255, 0, 206),
			new Color(255, 0, 207), new Color(255, 0, 208), new Color(255, 0, 209), new Color(255, 0, 210),
			new Color(255, 0, 211), new Color(255, 0, 212), new Color(255, 0, 213), new Color(255, 0, 214),
			new Color(255, 0, 215), new Color(255, 0, 216), new Color(255, 0, 217), new Color(255, 0, 218),
			new Color(255, 0, 219), new Color(255, 0, 220), new Color(255, 0, 221), new Color(255, 0, 222),
			new Color(255, 0, 223), new Color(255, 0, 224), new Color(255, 0, 225), new Color(255, 0, 226),
			new Color(255, 0, 227), new Color(255, 0, 228), new Color(255, 0, 229), new Color(255, 0, 230),
			new Color(255, 0, 231), new Color(255, 0, 232), new Color(255, 0, 233), new Color(255, 0, 234),
			new Color(255, 0, 235), new Color(255, 0, 236), new Color(255, 0, 237), new Color(255, 0, 238),
			new Color(255, 0, 239), new Color(255, 0, 240), new Color(255, 0, 241), new Color(255, 0, 242),
			new Color(255, 0, 243), new Color(255, 0, 244), new Color(255, 0, 245), new Color(255, 0, 246),
			new Color(255, 0, 247), new Color(255, 0, 248), new Color(255, 0, 249), new Color(255, 0, 250),
			new Color(255, 0, 251), new Color(255, 0, 252), new Color(255, 0, 253), new Color(255, 0, 254),
			new Color(255, 0, 255) };

	public static final ColorMap DEFAULT = new ColorMap(defaultColors);

	public static final ColorMap BLACKTORED = BLACKTORED();

	public static final ColorMap REDTOBLACK = BLACKTORED().reverse();

	public static final ColorMap BLACKTOGREEN = BLACKTOGREEN();

	public static final ColorMap GREENTOBLACK = BLACKTOGREEN().reverse();

	private static final ColorMap BLACKTORED() {
		List<Color> c = new ArrayList<Color>(256);
		for (int i = 0; i < 256; i++) {
			c.add(new Color(i, 0, 0));
		}
		return new ColorMap(c);
	}

	private static final ColorMap BLACKTOGREEN() {
		List<Color> c = new ArrayList<Color>(256);
		for (int i = 0; i < 256; i++) {
			c.add(new Color(0, i, 0));
		}
		return new ColorMap(c);
	}

	private ColorMap(List<Color> colors) {
		this.colors = colors;
	}

	public ColorMap(Color... colors) {
		this.colors = Arrays.asList(colors);
	}

	public int size() {
		return colors.size();
	}

	public Color get(int index) {
		index = index < 0 ? 0 : index;
		index = index >= colors.size() ? colors.size() - 1 : index;
		return colors.get(index);
	}

	public void set(int index, Color color) {
		index = index < 0 ? 0 : index;
		index = index >= colors.size() ? colors.size() - 1 : index;
		colors.set(index, color);
	}

	public ColorMap reverse() {
		List<Color> reversedColors = new ArrayList<Color>(colors);
		return new ColorMap(reversedColors);
	}

	public ColorMap smallerSize(int everyNth) {
		List<Color> newColors = new ArrayList<Color>(colors.size() / everyNth);
		for (int i = 0; i < colors.size(); i += everyNth) {
			newColors.add(colors.get(i));
		}
		return new ColorMap(newColors);
	}

}
