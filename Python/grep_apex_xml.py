import xml.etree.ElementTree as ET

tree = ET.parse('gsm_init_portfolio_APBNDLNC_GSMF0100.1031.1.20231226T0700-05.xml')
root = tree.getroot()

# 要保留的 ISIN 元素的列表
target_isins = ["US45866FAK03", "XS2634234376", "XS2634234020"]

elems_to_remove = []

for elem in root.iter('instrument'):

  keep = False

  for xref in elem.iter('xref'):
    if xref.get('type') == "ISIN" and xref.text in target_isins:
      keep = True
      break

  if not keep:
    elems_to_remove.append(elem)

payload = tree.find('payload')

for elem in elems_to_remove:
  payload.remove(elem)


tree.write('output.xml')
print("Grep xml Done")
