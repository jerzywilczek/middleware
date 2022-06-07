import Ice
import sys
import IntelligentHouse
# import intelligent_house_ice as IntelligentHouse
import ih


def handle_command_bulbulator(b: IntelligentHouse.BulbulatorPrx):
    print('Available commands: "get active" "set active" "get bul bul intensity" "set bul bul intensity"')
    command = input().strip()
    if command == 'get active':
        print(f'{b.getActive()}')
    elif command == 'set active':
        print('choose state to set (on or off)')
        state = input().strip()
        if state == 'on':
            b.setActive(True)
        elif state == 'off':
            b.setActive(False)
        else:
            print('Bad argument')
    elif command == 'get bul bul intensity':
        print(f'{b.getBulBulIntensity()}')
    elif command == 'set bul bul intensity':
        print('choose intensity to set (a value between 0.0 and 1.0)')
        intensity = float(input().strip())
        try:
            b.setBulBulIntensity(intensity)
        except IntelligentHouse.BulBulIntensityNotInRange:
            print('Value outside of range [0.0, 1.0]')
    else:
        print('bad command')


def handle_command_food_preparer(fp: IntelligentHouse.FoodPreparerPrx, command: str) -> bool:
    if command == 'get time to prepare food':
        print(fp.timeToPrepareFood())
        return True
    elif command == 'is currently cooking':
        print(fp.cookingInProgress())
        return True
    elif command == 'time left to finish cooking':
        try:
            print(fp.timeLeftToFinishCookingFood())
        except IntelligentHouse.NotCookingFoodCurrently:
            print('this device is not currently cooking any food')
        finally:
            return True
    return False


def handle_command_eggs_machine(em: IntelligentHouse.EggsMachine, command: str) -> bool:
    if handle_command_food_preparer(em, command):
        return True
    if command == 'get stored eggs amount':
        print(em.storedEggsAmount())
        return True
    return False


str_to_egg_hardness = {
    's': IntelligentHouse.EggHardnessLevel.SOFT,
    'm': IntelligentHouse.EggHardnessLevel.MEDIUM,
    'h': IntelligentHouse.EggHardnessLevel.HARD,
}


def handle_command_eggs_boiler(eb: IntelligentHouse.EggsBoilerPrx):
    print('Available commands: "get time to prepare food" "is currently cooking" "time left to finish cooking" "get stored eggs amount" "boil eggs"')
    command = input().strip()
    if handle_command_eggs_machine(eb, command):
        return
    elif command == 'boil eggs':
        print('please choose eggs amount')
        n = int(input())
        print('please choose egg hardness: [s]oft, [m]edium, [h]ard')
        hardness = input().strip()
        if hardness not in str_to_egg_hardness:
            print('bad hardness')
            return
        hardness = str_to_egg_hardness[hardness]
        try:
            eb.boilEggs(n, hardness)
        except IntelligentHouse.NotEnoughEggsInStorage:
            print('there is not enough eggs in storage')
        except IntelligentHouse.CookingAlreadyInProgress:
            print('cooking is already in progress')
    else:
        print('bad command')


str_to_additional_ingredient = {
    'p': IntelligentHouse.ScrambledEggsAdditionalIngredient.PEPPER,
    'o': IntelligentHouse.ScrambledEggsAdditionalIngredient.ONION,
    'c': IntelligentHouse.ScrambledEggsAdditionalIngredient.CHIVES,
    't': IntelligentHouse.ScrambledEggsAdditionalIngredient.TOMATO,
    'm': IntelligentHouse.ScrambledEggsAdditionalIngredient.MUSHROOMS,
}


def handle_command_scrambled_eggs_maker(sem: IntelligentHouse.ScrambledEggsMakerPrx):
    print(
        'Available commands: "get time to prepare food" "is currently cooking" "time left to finish cooking" "get stored eggs amount" "prepare scrambled eggs"')
    command = input().strip()
    if handle_command_eggs_machine(sem, command):
        return
    elif command == 'prepare scrambled eggs':
        print('please choose eggs amount')
        n = int(input())
        print('please choose scrambled eggs hardness: [s]oft, [m]edium, [h]ard')
        hardness = input().strip()
        if hardness not in str_to_egg_hardness:
            print('bad hardness')
            return
        hardness = str_to_egg_hardness[hardness]
        print('please choose additional ingredients (enter a space separated list)')
        print('[p]epper')
        print('[o]nion')
        print('[c]hives')
        print('[t]omato')
        print('[m]ushrooms')
        ingredients = list(map(lambda i: str_to_additional_ingredient[i], input().strip().split(' ')))
        try:
            sem.prepareScrambledEggs(n, hardness, ingredients)
        except IntelligentHouse.NotEnoughEggsInStorage:
            print('there is not enough eggs in storage')
        except IntelligentHouse.CookingAlreadyInProgress:
            print('cooking is already in progress')
    else:
        print('bad command')


str_to_tea_type = {
    'e': IntelligentHouse.TeaType.EARLGRAY,
    'o': IntelligentHouse.TeaType.OOLONG,
    'g': IntelligentHouse.TeaType.GUNPOWDER,
}


def handle_command_tea_maker(tm: IntelligentHouse.TeaMakerPrx):
    print(
        'Available commands: "get time to prepare food" "is currently cooking" "time left to finish cooking" "start making tea"')
    command = input().strip()
    if handle_command_food_preparer(tm, command):
        return
    elif command == 'start making tea':
        print('please choose tea type: [e]arl gray, [o]olong, [g]unpowder')
        tea_type = input().strip()
        if tea_type not in str_to_tea_type:
            print('bad tea type')
            return
        tea_type = str_to_tea_type[tea_type]
        tm.startMakingTea(tea_type)
    else:
        print('bad command')


def handle_command_sitting_device(sd: IntelligentHouse.SittingDevice, command) -> bool:
    if command == 'extend':
        sd.extend()
        return True
    elif command == 'contract':
        sd.contract()
        return True
    return False


def handle_command_warm_armchair(wa: IntelligentHouse.WarmArmchairPrx):
    print(
        'Available commands: "extend" "contract" "set heating" "get heating"')
    command = input().strip()
    if handle_command_sitting_device(wa, command):
        return
    elif command == 'get heating':
        print(wa.getHeating())
    elif command == 'set heating':
        print('choose state to set (on or off)')
        state = input().strip()
        if state == 'on':
            wa.setActive(True)
        elif state == 'off':
            wa.setActive(False)
        else:
            print('Bad argument')
    else:
        print('bad command')


def handle_command_prince_polo_sofa(pps: IntelligentHouse.PrincePoloSofaPrx):
    print(
        'Available commands: "extend" "contract" "serve prince polo" "get prince polo amount in storage"')
    command = input().strip()
    if handle_command_sitting_device(pps, command):
        return
    elif command == 'get prince polo amount in storage':
        print(pps.princePoloAmountInStorage())
    elif command == 'serve prince polo':
        try:
            pps.servePrincePolo()
        except IntelligentHouse.PrincePoloStorageEmpty:
            print('prince polo storage is empty')
    else:
        print('bad command')


if __name__ == '__main__':
    with Ice.initialize(sys.argv) as communicator:
        eggs_boiler = communicator.stringToProxy("Cookers/eggsBoiler:tcp -h localhost -p 10000:udp -h localhost -p 10000")
        eggs_boiler: IntelligentHouse.EggsBoilerPrx = IntelligentHouse.EggsBoilerPrx.checkedCast(eggs_boiler)

        scrambled_eggs_maker = communicator.stringToProxy("Cookers/scrambledEggsMaker:tcp -h localhost -p 10000:udp -h localhost -p 10000")
        scrambled_eggs_maker: IntelligentHouse.ScrambledEggsMakerPrx = IntelligentHouse.ScrambledEggsMakerPrx.checkedCast(scrambled_eggs_maker)

        tea_maker_kuchnia = communicator.stringToProxy("Cookers/teaMakerKuchnia:tcp -h localhost -p 10001:udp -h localhost -p 10001")
        tea_maker_kuchnia: IntelligentHouse.TeaMakerPrx = IntelligentHouse.TeaMakerPrx.checkedCast(tea_maker_kuchnia)

        tea_maker_biuro = communicator.stringToProxy("Cookers/teaMakerBiuro:tcp -h localhost -p 10001:udp -h localhost -p 10001")
        tea_maker_biuro: IntelligentHouse.TeaMakerPrx = IntelligentHouse.TeaMakerPrx.checkedCast(tea_maker_biuro)

        warm_armchair_salon_po_prawej = communicator.stringToProxy("SittingDevices/warmArmchairSalonPoPrawej:tcp -h localhost -p 10001:udp -h localhost -p 10001")
        warm_armchair_salon_po_prawej: IntelligentHouse.WarmArmchairPrx = IntelligentHouse.WarmArmchairPrx.checkedCast(warm_armchair_salon_po_prawej)

        warm_armchair_salon_po_lewej = communicator.stringToProxy("SittingDevices/warmArmchairSalonPoLewej:tcp -h localhost -p 10001:udp -h localhost -p 10001")
        warm_armchair_salon_po_lewej: IntelligentHouse.WarmArmchairPrx = IntelligentHouse.WarmArmchairPrx.checkedCast(warm_armchair_salon_po_lewej)

        prince_polo_sofa_salon = communicator.stringToProxy("SittingDevices/princePoloSofaSalon:tcp -h localhost -p 10001:udp -h localhost -p 10001")
        prince_polo_sofa_salon: IntelligentHouse.PrincePoloSofaPrx = IntelligentHouse.PrincePoloSofaPrx.checkedCast(prince_polo_sofa_salon)

        while True:
            print('Please select device (pick number):')
            for i, d in enumerate(ih.devices):
                print(f'{i + 1}. {d}')
            print('Or type "bulbulator n" to access nth bulbulator (eg. "bulbulator 2")')
            device = input().strip()
            if device == '1':
                handle_command_eggs_boiler(eggs_boiler)
            elif device == '2':
                handle_command_scrambled_eggs_maker(scrambled_eggs_maker)
            elif device == '3':
                handle_command_tea_maker(tea_maker_kuchnia)
            elif device == '4':
                handle_command_tea_maker(tea_maker_biuro)
            elif device == '5':
                handle_command_warm_armchair(warm_armchair_salon_po_prawej)
            elif device == '6':
                handle_command_warm_armchair(warm_armchair_salon_po_prawej)
            elif device == '7':
                handle_command_prince_polo_sofa(prince_polo_sofa_salon)
            elif device.split(" ")[0] == 'bulbulator':
                nr = int(device.split(" ")[1])
                handle_command_bulbulator(
                    IntelligentHouse.BulbulatorPrx.checkedCast(
                        communicator.stringToProxy(f"Bulbulators/bulbulator{nr}:tcp -h localhost -p 10000:udp -h localhost -p 10000")
                    )
                )
