#!/bin/env python3
"""
This file generates a whole list of resources for the TerraFirmaCraft mod.
Any resource files generated by this script should set a root JSON tag:
    "__comment": "Generated by generateResources.py function: model"

You should set this script up to run automatically whenever you launch the game, and make sure it's run before you commit.
For IntelliJ instructions, see README.md.
"""

import json
import os
import time
import zipfile


def zipfolder(zip_name, target_dir):
    zipobj = zipfile.ZipFile(zip_name, 'w', zipfile.ZIP_DEFLATED)
    rootlen = len(target_dir) + 1
    for base, dirs, files in os.walk(target_dir):
        for file in files:
            fn = os.path.join(base, file)
            zipobj.write(fn, fn[rootlen:])


if not os.path.isdir('assets_backups'):
    os.mkdir('assets_backups')
    with open('assets_backups/.gitignore', 'w') as f:
        print(
            '# This folder does not belong on git. Not even as an empty folder, so we ignore everything, incl. this file.',
            file=f)
        print('*', file=f)

zipfolder('assets_backups/{}.zip'.format(int(time.time())), 'src/main/resources/assets/tfc')

os.chdir('src/main/resources/assets/tfc/')

ORE_TYPES = {
    'native_ardite': True,
    'native_osmium': True,
    'native_mithril': True,
    'bauxite': True,
    'scheelite': True,
    'pitchblende': True,
    'cobaltite': True,
}

METAL_TYPES = {
    'aluminium': True,
    'constantan': False,
    'tungsten': True,
    'cobalt': True,
    'ardite': True,
    'manyullin': True,
    'osmium': True,
    'invar': True,
    'electrum': False,
    'cast_iron': False,
    'mithril': True,
    'uranium': False,
}  # + unknown

METAL_ITEMS = {
    # 'unshaped': False, Special
    'ingot': False,
    'double_ingot': False,
    'scrap': False,
    'dust': False,
    'nugget': False,
    'sheet': False,
    'double_sheet': False,
    'anvil': True,
    'tuyere': True,
    'lamp': False,
    'pick': True,
    'pick_head': True,
    'shovel': True,
    'shovel_head': True,
    'axe': True,
    'axe_head': True,
    'hoe': True,
    'hoe_head': True,
    'chisel': True,
    'chisel_head': True,
    'sword': True,
    'sword_blade': True,
    'mace': True,
    'mace_head': True,
    'saw': True,
    'saw_blade': True,
    'javelin': True,
    'javelin_head': True,
    'hammer': True,
    'hammer_head': True,
    'propick': True,
    'propick_head': True,
    'knife': True,
    'knife_blade': True,
    'scythe': True,
    'scythe_blade': True,
    'shears': True,
    'unfinished_chestplate': True,
    'chestplate': True,
    'unfinished_greaves': True,
    'greaves': True,
    'unfinished_boots': True,
    'boots': True,
    'unfinished_helmet': True,
    'helmet': True,
}

TOOLS = [
    'pick', 'propick', 'shovel', 'axe', 'hoe', 'chisel', 'sword', 'mace', 'saw', 'shears', 'javelin', 'hammer', 'knife',
    'scythe'
]


def del_none(d):
    """
    https://stackoverflow.com/a/4256027/4355781
    Modifies input!
    """
    for key, value in list(d.items()):
        if value is None:
            del d[key]
        elif isinstance(value, dict):
            del_none(value)
    return d


def blockstate(filename_parts, model, textures, variants=None):
    """
    Magic.
    :param filename_parts: Iterable of strings.
    :param model: String or None
    :param textures: Dict of <string>:<string> OR <iterable of strings>:<string>
    :param variants: Dict of <string>:<variant> OR "normal":None (to disable the normal default)
    """
    _variants = {
        'normal': [{}]
    }
    if variants:
        _variants.update(variants)

    # Unpack any tuple keys to simple string->string pairs
    _textures = {}
    for key, val in textures.items():
        if isinstance(key, str):
            _textures[key] = val
        else:
            for x in key:
                _textures[x] = val

    p = os.path.join('blockstates', *filename_parts) + '.json'
    os.makedirs(os.path.dirname(p), exist_ok=True)
    with open(p, 'w') as file:
        json.dump(del_none({
            '__comment': 'Generated by generateResources.py function: blockstate',
            'forge_marker': 1,
            'defaults': {
                'model': model,
                'textures': _textures,
            },
            'variants': _variants,
        }), file, indent=2)


def cube_all(filename_parts, texture, variants=None, model='cube_all'):
    blockstate(filename_parts, model, textures={'all': texture}, variants=variants)


def model(filename_parts, parent, textures):
    p = os.path.join('models', *filename_parts) + '.json'
    os.makedirs(os.path.dirname(p), exist_ok=True)
    with open(p, 'w') as file:
        json.dump(del_none({
            '__comment': 'Generated by generateResources.py function: model',
            'parent': parent,
            'textures': textures,
        }), file, indent=2)


def item(filename_parts, *layers, parent='item/generated'):
    model(('item', *filename_parts), parent,
          None if len(layers) == 0 else {'layer%d' % i: v for i, v in enumerate(layers)})


#   ____  _            _        _        _
#  |  _ \| |          | |      | |      | |
#  | |_) | | ___   ___| | _____| |_ __ _| |_ ___  ___
#  |  _ <| |/ _ \ / __| |/ / __| __/ _` | __/ _ \/ __|
#  | |_) | | (_) | (__|   <\__ \ || (_| | ||  __/\__ \
#  |____/|_|\___/ \___|_|\_\___/\__\__,_|\__\___||___/
#
# BLOCKSTATES

# METAL FLUIDS
for name in METAL_TYPES.keys():
    blockstate(('fluid', name), 'forge:fluid', {}, {
        'normal': {
            'transform': "forge:default-item",
            'custom': {
                'fluid': name
            }
        }
    })

# ANVILS
for key in METAL_TYPES:
    if METAL_TYPES[key]:
        blockstate(('anvil', key), 'tfc:anvil', textures={
            ('all', 'particle'): 'tfc:blocks/metal/%s' % key
        }, variants={
            'axis': {
                'north': {'y': 180},
                'east': {'y': 270},
                'south': {},
                'west': {'y': 90}
            }
        })

# METAL SHEETS
for key in METAL_TYPES:
    blockstate(('sheet', key), 'tfc:empty', textures={
        ('all', 'particle'): 'tfc:blocks/metal/%s' % key
    }, variants={
        'normal': None,
        "north": {"true": {"submodel": {"north": {"model": "tfc:sheet", "x": 90}}}, "false": {}},
        "south": {"true": {"submodel": {"south": {"model": "tfc:sheet", "y": 180, "x": 90}}}, "false": {}},
        "east": {"true": {"submodel": {"east": {"model": "tfc:sheet", "y": 90, "x": 90}}}, "false": {}},
        "west": {"true": {"submodel": {"west": {"model": "tfc:sheet", "y": 270, "x": 90}}}, "false": {}},
        "up": {"true": {"submodel": {"up": {"model": "tfc:sheet"}}}, "false": {}},
        "down": {"true": {"submodel": {"down": {"model": "tfc:sheet", "x": 180}}}, "false": {}}
    })


#   _____ _
#  |_   _| |
#    | | | |_ ___ _ __ ___  ___
#    | | | __/ _ \ '_ ` _ \/ __|
#   _| |_| ||  __/ | | | | \__ \
#  |_____|\__\___|_| |_| |_|___/
# 
# ITEMS

# ORES
for ore_type in ORE_TYPES:
    if ORE_TYPES[ore_type]:
        for grade in ['poor', 'rich', 'small']:
            item(('ore', grade, ore_type), 'tfc:items/ore/%s/%s' % (grade, ore_type))
    item(('ore', 'normal', ore_type), 'tfc:items/ore/%s' % ore_type)

# METALS
for item_type, tool_item in METAL_ITEMS.items():
    for metal, tool_metal in METAL_TYPES.items():
        if tool_item and not tool_metal:
            continue
        if item_type == 'anvil':
            model(('item', 'metal', 'anvil', metal), 'tfc:item/metal/anvil/transformations',
                  {'all': 'tfc:blocks/metal/%s' % metal})
        else:
            parent = 'item/handheld' if item_type in TOOLS else 'item/generated'
            if item_type in ['knife', 'javelin']:
                parent = 'tfc:item/handheld_flipped'
            item(('metal', item_type, metal), 'tfc:items/metal/%s/%s' % (item_type.replace('unfinished_', ''), metal),
                 parent=parent)

for metal in METAL_TYPES.keys():
    item(('ceramics', 'fired', 'mold', 'ingot', metal), 'tfc:items/ceramics/fired/mold/ingot/' + metal)