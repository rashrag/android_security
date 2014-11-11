import re
fp = open("permissions.txt","r")
fp2 = open("goodware.txt","w+")
lines = fp.readlines()
count = 0
print(len(lines))
permissionList = []
permissionDict = {}
appPermission = {}
for i in lines:
    names = i.split(":")
    permission = []
    permission = re.sub(r'(\d+)\)'," ",names[1])
    permission.strip()
    permissions = [i.strip() for i in permission.split(" ")]
    permissions.pop(0)
    appPermission[names[0]] = permissions
    val = len(permissions) - 1
    fp2.write(names[0]+": ");
    for i in range(val):
        fp2.write(permissions[i]+", ")
    try:
        fp2.write(permissions[val])
    except:
        print(val,len(permissions),permissions)
    fp2.write(" ; GOOD");
    fp2.write("\n")
    #break
    # permissionList.extend(permissions)
# for i in permissionList:
    # if i not in permissionDict:
        # permissionDict[i] = i
# permissionList = permissionDict.keys()
# for appNames in appPermission:
    # permList = appPermission[appNames]
    # fp2.write(appNames + ":\n")
    # for permissions in permissionList:
        # if permissions in permList:
            # fp2.write('1 ')
        # else:
            # fp2.write('0 ')
    # fp2.write('\n')


# fp2.close()
