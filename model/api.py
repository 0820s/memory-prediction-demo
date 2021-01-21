"""
0 - Dell
1 - Foxconn
2 - Inspur
3 - Inventec
"""
def mapBrand(inp):
  if str(inp) == 'Dell':
    return 0
  elif str(inp) == 'Foxconn':
    return 1
  elif str(inp) == 'Inspur':
    return 2
  elif str(inp) == 'Inventec':
    return 3
  else:
    mapping = ['Dell', 'Foxconn', 'Inspur', 'Inventec']
    return mapping[int(inp)]

"""
0 - Broadwell
1 - Skylake
"""
def mapPlat(inp):
  if str(inp) == 'Broadwell':
    return 0
  elif str(inp) == 'Skylake':
    return 1
  else:
    mapping = ['Broadwell', 'Skylake']
    return mapping[int(inp)]

"""
0 - Hynix
1 - Micron
2 - Samsung
"""
def mapManu(inp):
  if str(inp) == 'Hynix':
    return 0
  elif str(inp) == 'Micron':
    return 1
  elif str(inp) == 'Samsung':
    return 2
  else:
    mapping = ['Hynix', 'Micron', 'Samsung']
    return mapping[int(inp)]

"""
0 - 2400 MHz
1 - 2666 MHz
"""
def mapSpeed(inp):
  if str(inp) == '2400 MHz':
    return 0
  elif str(inp) == '2666 MHz':
    return 1
  else:
    mapping = ['2400 MHz', '2666 MHz']
    return mapping[int(inp)]

"""
0 - 16nm
1 - 18nm
2 - 20nm
"""
def mapProc(inp):
  if str(inp) == '16nm':
    return 0
  elif str(inp) == '18nm':
    return 1
  elif str(inp) == '20nm':
    return 2
  else:
    mapping = ['16nm', '18nm', '20nm']
    return mapping[int(inp)]
